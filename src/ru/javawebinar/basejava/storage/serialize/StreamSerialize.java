package ru.javawebinar.basejava.storage.serialize;

import ru.javawebinar.basejava.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerialize {
   Resume doRead(InputStream is);

   void doWrite(Resume r, OutputStream os);
}
