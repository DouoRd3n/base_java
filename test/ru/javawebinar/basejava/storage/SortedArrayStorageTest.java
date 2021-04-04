package ru.javawebinar.basejava.storage;

import org.junit.BeforeClass;

public class SortedArrayStorageTest extends AbstractStorageTest {

    //    public SortedArrayStorageTest() {
//        super(new SortedArrayStorage());
//    }
    @BeforeClass
    public static void initialize() {
        AbstractStorageTest.storage = new SortedArrayStorage();
    }
}
