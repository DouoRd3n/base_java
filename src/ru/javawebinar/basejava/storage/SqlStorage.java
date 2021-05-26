package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ExceptionUtil;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    addContact(rs, r);


                    return r;
                });
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        do{

            String value = rs.getString("value");
            if (value != null){
                ContactType type = ContactType.valueOf(rs.getString("type"));
                r.addContact(type, value);
            }

        }while (rs.next());
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try(PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid() );
                ps.executeUpdate();
            }
            deleteContacts(conn, r);
            insertContacts(conn,r);





            return null;
        });

    }

    private void deleteContacts(Connection conn, Resume r) {
        try(PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")){
            ps.setString(1, r.getUuid());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    insertContacts(conn, r);

                    return null;
                }
        );
    }

    private void insertContacts(Connection conn, Resume r) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            ExceptionUtil.convertException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }
// LEFT JOIN contact c " +
//                        "        ON r.uuid = c.resume_uuid " +
//                        "     WHERE r.uuid =? "
    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume r  ORDER BY full_name,uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            Resume r;
            while (rs.next()) {
                r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                setContacts(r);
                resumes.add(r);
            }
            return resumes;
        });
    }

    private void setContacts(Resume r) {
        sqlHelper.execute("SELECT * FROM contact where resume_uuid = ?", ps->{
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
            }
            return null;
        });

    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}