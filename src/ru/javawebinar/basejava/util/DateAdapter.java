package ru.javawebinar.basejava.util;

import java.time.LocalDate;

public class DateAdapter {
    public static LocalDate unmarshal(String str) throws Exception {
        return LocalDate.parse(str);
    }


    public static String marshal(LocalDate ld) throws Exception {
        return ld.toString();
    }
}
