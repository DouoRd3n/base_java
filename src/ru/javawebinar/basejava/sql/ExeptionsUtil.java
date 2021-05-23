package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.SQLException;

public class ExeptionsUtil extends Exception{
    private ExeptionsUtil(){

    }
    public static StorageException convertExeptions(SQLException e){
        if (e instanceof SQLException){
            if(e.getSQLState().equals("23505")){
                throw new ExistStorageException(null);
            }
        }
        return new StorageException(e);
    }
}
