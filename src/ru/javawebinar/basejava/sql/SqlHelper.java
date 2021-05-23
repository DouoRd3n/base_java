package ru.javawebinar.basejava.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    public void execute(String sql){
        execute(sql, ps-> ps.execute());
    }

    public <T> T execute(String sql, SqlExecutor<T> executor){
        try(Connection conn = connectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw ExeptionsUtil.convertExeptions(e);
        }

    }
}
