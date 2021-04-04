package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

   private final List<Resume> storage = new ArrayList<>();


    @Override
    protected void doSave(Object index, Resume r) {
        storage.add(r);
    }

    @Override
    protected void doDelete(Object index) {
        storage.remove(((Integer) index).intValue());
    }

    @Override
    protected Resume doGet(Object index) {
        return storage.get((Integer) index);
    }

    @Override
    protected void doUpdate(Object index, Resume r) {
        storage.add((Integer)index, r);

    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer)index !=null ;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i <storage.size() ; i++) {
            if (storage.get(i).getUuid().equals(uuid)){
                return (Integer) i;
            }

        }
        return null;
    }

    @Override
    public void clear() {
        storage.clear();

    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
