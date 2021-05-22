package ru.javawebinar.basejava.storage;

public abstract class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "gstnbkde"));
    }
}
