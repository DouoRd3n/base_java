package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

   private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {

        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
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
        return 0;
    }
}
