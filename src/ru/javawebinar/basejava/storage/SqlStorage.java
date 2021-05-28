package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO implement Section (except OrganizationSection)
// TODO Join and split ListSection by `\n`
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
                    do {
                        addContact(rs, r);

                    } while (rs.next());
                        addSections(r);
                    return r;
                });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(conn, r);
            insertContact(conn, r);
            deleteSections(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    private void deleteSections(Connection conn, Resume r) {
        sqlHelper.execute("DELETE FROM section WHERE resume_uuid = ?", ps->{
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContact(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    private void insertSections(Connection conn, Resume r) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue().toString());
                ps.addBatch();

            }
            ps.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume r  ORDER BY full_name,uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            Resume r;
            while (rs.next()) {
                r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                addContacts(r);
                addSections(r);
                resumes.add(r);
            }
            return resumes;
        });
    }

    private void addSections(Resume r) {
        sqlHelper.execute("SELECT *FROM section where resume_uuid=?", ps->{
            ps.setString(1,r.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String type = rs.getString("type");
                switch (type){

                    case "PERSONAL":
                    case "OBJECTIVE" :
                        r.addSection(SectionType.valueOf(type), new TextSection(rs.getString("value")));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        r.addSection(SectionType.valueOf(type),newListSections(rs));
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                }

            }
            return null;
        });

    }

    private ListSection newListSections(ResultSet rs) throws SQLException {
        List<String> list;
        String value = rs.getString("value");
        String[] split = value.substring(1, value.length() - 1).split(",");
        list = new ArrayList<>();
        for (int i = 0; i < split.length ; i++) {
            list.add(split[i].trim());
        }
        return  new ListSection(list);
    }

    private void addContacts(Resume r) {
        sqlHelper.execute("SELECT * FROM contact where resume_uuid = ?", ps -> {
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
            }
            addSections(r);
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

    private void insertContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume r) {
        sqlHelper.execute("DELETE  FROM contact WHERE resume_uuid=?", ps -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }
}
