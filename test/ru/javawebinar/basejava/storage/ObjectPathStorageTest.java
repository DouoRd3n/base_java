package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialize.ObjectStreamSerializer;

import java.nio.file.Path;

public class ObjectPathStorageTest extends AbstractStorageTest{

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer()));
    }
}
