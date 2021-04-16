package ru.javawebinar.basejava;

import ru.javawebinar.basejava.exception.StorageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * gkislin
 * 21.07.2016
 */
public class MainFile {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Serg\\IdeaProjects\\base_java\\src";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }
        deepОutputOfFilesAndDirectories(filePath);

//        try (FileInputStream fis = new FileInputStream(filePath)) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }
        public static void deepОutputOfFilesAndDirectories(String filePath){
        File dir = new File(filePath);
            File[] files = dir.listFiles();
            if (files == null){
                throw new StorageException("Directory is emtry", null);
            }
            for (File file : files) {
                if (file.isFile()){
                    System.out.println("File: " + file.getName());
                } else if (file.isDirectory()){
                    System.out.println("Directory: " + file.getName());
                    deepОutputOfFilesAndDirectories(filePath + "\\" + file.getName());
                }
            }

        }



}
