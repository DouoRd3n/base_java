package ru.javawebinar.basejava.storage;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    public static void initialize(){
        AbstractStorageTest.storage = new MapStorage();
    }
}
