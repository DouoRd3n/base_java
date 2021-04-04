package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.BeforeClass;

public class ArrayStorageTest extends AbstractStorageTest {

//    public ArrayStorageTest() {
//        super(new ArrayStorage());
//    }
@BeforeClass
public static void initialize(){
        AbstractStorageTest.storage = new ArrayStorage();
    }

}