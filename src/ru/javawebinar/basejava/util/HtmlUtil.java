package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.Organization;

public class HtmlUtil {
    public static String dateFormat(Organization.Position position){
        return DateUtil.format(position.getStartDate()) + "-" + position.getEndDate();
    }
}
