package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage{
    private final Map<Object, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(Object searchKey, Resume r) {
        if (searchKey == null){
            storage.put(r.getUuid(), r);
        }

    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey) {

        return storage.get(searchKey);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume r) {
        storage.put(searchKey, r);
    }

    @Override
    protected boolean isExist(Object searchKey) {

        return searchKey != null;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (Map.Entry<Object, Resume> me : storage.entrySet()) {
          if(me.getKey().toString().equals(uuid)){
              return me.getKey();
          }

        }        return null;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
