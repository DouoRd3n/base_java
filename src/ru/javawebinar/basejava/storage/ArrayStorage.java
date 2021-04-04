package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void fillDeletedElement(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected void insertElement(Resume r, int index) {
        storage[size] = r;
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doSave(Resume r) {

    }

    @Override
    protected void doDelete(int searchKey) {

    }

    @Override
    protected Resume doGet(int searchKey) {
        return null;
    }

    @Override
    protected void doUpdate(int searchKey, Resume r) {

    }

    @Override
    protected boolean isExist(int r) {
        return false;
    }

    @Override
    protected int getSearchKey(String uuid) {
        return getIndex(uuid);
    }
}
