package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;

import java.sql.SQLException;

public class ExeptionsUtil extends Exception{
    private ExeptionsUtil(){

    }
    public static void convertExeptions(SQLException e){
        if (e instanceof SQLException){
            if(e.getSQLState().equals("")){
                throw new ExistStorageException(e)
            }
        }
    }
}
