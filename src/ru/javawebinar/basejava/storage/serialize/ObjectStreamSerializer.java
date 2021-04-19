package ru.javawebinar.basejava.storage.serialize;

import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public class ObjectStreamSerializer implements StreamSerialize{
    @Override
    public Resume doRead(InputStream is) throws IOException {
        try(ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       ;
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }
}
