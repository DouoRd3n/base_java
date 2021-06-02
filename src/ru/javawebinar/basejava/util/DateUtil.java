package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 * gkislin
 * 20.07.2016
 */
public class DateUtil {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/yyyy");

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate ld){
        if (ld == null){
            return "";
        }else {
          return ld.equals(NOW)? "Сейчас" : ld.format(dateTimeFormatter);
        }
    }
}