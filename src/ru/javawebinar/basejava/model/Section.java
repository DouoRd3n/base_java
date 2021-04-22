package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * gkislin
 * 19.07.2016
 */
@XmlAccessorType(XmlAccessType.FIELD)
abstract public class Section implements Serializable {


    public List<String> toList(){
        return new ArrayList<>();
    }
}