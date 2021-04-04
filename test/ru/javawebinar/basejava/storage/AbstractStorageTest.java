package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorageTest {

    protected static Storage storage;

    protected static final String UUID_1 = "uuid1";
    public static final Resume RESUME_1 = new Resume(UUID_1);
    protected static final String UUID_2 = "uuid2";
    public static final Resume RESUME_2 = new Resume(UUID_2);
    protected static final String UUID_3 = "uuid3";
    public static final Resume RESUME_3 = new Resume(UUID_3);
    public static final String UUID_4 = "uuid4";
    public static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {


        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }



    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());

    }

    @Test
    public void update() {
        Resume newResume = new Resume("uuid5");
        storage.save(newResume);
        Assert.assertTrue(newResume == storage.get("uuid5"));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] storageAll = storage.getAll();

        Assert.assertEquals(3, storageAll.length);


    }


    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        Assert.assertEquals(RESUME_4, storage.get(RESUME_4.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_3);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void get() throws Exception {
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }






}