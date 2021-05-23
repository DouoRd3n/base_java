package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });

    }

    @Override
    public void update(Resume r) {
        sqlHelper.execute("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1,r.getFullName());
            ps.setString(2,r.getUuid());
            if (ps.executeUpdate() == 0){
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        } );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2,r.getFullName());
           boolean b =  ps.execute();

           return null;
        });

    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate()==0){
                throw new NotExistStorageException(uuid);
            }
           return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> list = new ArrayList<>();
        sqlHelper.execute("SELECT * FROM resume r ORDERED BY (uuid, full_name)", ps->{
           ResultSet rs =  ps.executeQuery();
           while (rs.next()){
               list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
           }
        });
        return null;
    }

    @Override
    public int size() {

      return   sqlHelper.execute("SELECT count(*)FROM resume", ps ->{
           ResultSet rs = ps.executeQuery();
          return rs.next()? rs.getInt(1):1;
        }) ;
    }
}
