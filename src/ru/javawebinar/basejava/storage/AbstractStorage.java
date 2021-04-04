package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        Object searchKey =  getNotExistedKey(r.getUuid());
        doUpdate(searchKey, r);
    }


    public void save(Resume r) {
        Object searchKey = getExistedKey(r.getUuid());
        doSave(searchKey, r);
    }



    public void delete(String uuid) {
        Object searchKey = getNotExistedKey(uuid);
        doDelete(searchKey);
    }



    public Resume get(String uuid) {
        Object searchKey = getNotExistedKey(uuid);
        return doGet(searchKey);
    }

    protected abstract void doSave(Object searchKey, Resume r);

    protected abstract void doDelete(Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume r);

    protected abstract boolean isExist(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected Object getExistedKey(String uuid){
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
    protected Object getNotExistedKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}
