package ru.javawebinar.basejava.storage;

import org.junit.BeforeClass;

public class ListStorageTest extends AbstractStorageTest {
    @BeforeClass
    public static void initialize(){
        AbstractStorageTest.storage = new ListStorage();
    }
}
