package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = StorageException.class)
    public void saveOverFlov() {

        try {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume());

    }

    @Override
    public void getAll() throws Exception {
        Resume[] storageAll = storage.getAll();
        Assert.assertEquals(3, storageAll.length);
        Assert.assertEquals(RESUME_1, storageAll[0]);
        Assert.assertEquals(RESUME_2, storageAll[1]);
        Assert.assertEquals(RESUME_3, storageAll[2]);
    }
}
