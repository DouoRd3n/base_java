package ru.javawebinar.basejava;

import org.junit.Before;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.PathStorage;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.storage.serializer.DataStreamSerializer;

import java.io.File;
import java.time.Month;

public class MainDataStream {


    public static void main(String[] args) {
        File STORAGE_DIR = new File("C:\\Users\\Serg\\IdeaProjects\\base_java\\storage");

        Storage storage = new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer());

String UUID_1 = "uuid1";
String UUID_2 = "uuid2";
 String UUID_3 = "uuid3";
final String UUID_4 = "uuid4";

Resume R1;
Resume R2;
Resume R3;
Resume R4;


            R1 = new Resume(UUID_1, "Name1");
            R2 = new Resume(UUID_2, "Name2");
            R3 = new Resume(UUID_3, "Name3");
            R4 = new Resume(UUID_4, "Name4");

            R1.addContact(ContactType.MAIL, "mail1@ya.ru");
            R1.addContact(ContactType.PHONE, "11111");
            R1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
            R1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
            R1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achivment11", "Achivment12", "Achivment13"));
            R1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
            R1.addSection(SectionType.EXPERIENCE,
                    new OrganizationSection(
                            new Organization("Organization11", "http://Organization11.ru",
                                    new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
                                    new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
            R1.addSection(SectionType.EDUCATION,
                    new OrganizationSection(
                            new Organization("Institute", null,
                                    new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                    new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                            new Organization("Organization12", "http://Organization12.ru")));
            R2.addContact(ContactType.SKYPE, "skype2");
            R2.addContact(ContactType.PHONE, "22222");
            R1.addSection(SectionType.EXPERIENCE,
                    new OrganizationSection(
                            new Organization("Organization2", "http://Organization2.ru",
                                    new Organization.Position(2015, Month.JANUARY, "position1", "content1"))));




            storage.clear();
            storage.save(R1);
//            storage.save(R2);
//            storage.save(R3);
        SectionType[] values = SectionType.values();
        for (int i = 0; i <values.length ; i++) {
            System.out.println(storage.get(UUID_1).getSection(values[i]));
        }


    }
}
