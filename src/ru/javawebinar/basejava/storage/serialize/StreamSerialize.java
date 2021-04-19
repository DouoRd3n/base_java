package ru.javawebinar.basejava.storage.serialize;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerialize {
   Resume doRead(InputStream is) throws IOException;

   void doWrite(Resume r, OutputStream os) throws IOException;
}
