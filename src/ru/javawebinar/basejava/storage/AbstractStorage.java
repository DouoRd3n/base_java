package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        int searchKey = getSearchKey(r.getUuid());
        mustExist(r.getUuid(), searchKey);
        doUpdate(searchKey, r);

    }


    public void save(Resume r) {
        int searchKey = getSearchKey(r.getUuid());
        mustExist(r.getUuid(), searchKey);
        doSave(r);

    }

    protected abstract void doSave(Resume r);

    public void delete(String uuid) {
        int searchKey = getSearchKey(uuid);
        mustExist(uuid, searchKey);
        doDelete(searchKey);
    }



    public Resume get(String uuid) {
        int searchKey = getSearchKey(uuid);
        mustExist(uuid, searchKey);

        return doGet(searchKey);
    }
    protected abstract void doDelete(int searchKey);

    protected abstract Resume doGet(int searchKey);

    protected abstract void doUpdate(int searchKey, Resume r);

    protected abstract boolean isExist(int r);

    protected abstract int getSearchKey(String uuid);

    protected void mustExist(String uuid, int searchKey) {
        if (!isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
    }
}
