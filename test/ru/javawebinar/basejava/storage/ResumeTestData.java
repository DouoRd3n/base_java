package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("1","Григорий Кислин");
        TextSection objectiv = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        List<String> listAchievements = new ArrayList<>();
        listAchievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"," +
                " \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\"." +
                " Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        listAchievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, " +
                "Google Authenticator, Jira, Zendesk.");
        listAchievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. " +
                "Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных " +
                "ERP модулей, интеграция CIFS/SMB java сервера.");
        listAchievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), " +
                "Commet, HTML5, Highstock для алгоритмического трейдинга.");
        listAchievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, " +
                "JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для " +
                "администрирования и мониторинга системы по JMX (Jython/ Django).");
        listAchievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), " +
                "Белоруcсии(Erip, Osmp) и Никарагуа.");
        ListSection achievement = new ListSection(listAchievements);
        List<String> listQalifications = new ArrayList<>();
        listQalifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        listQalifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        listQalifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        listQalifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        listQalifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        listQalifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        listQalifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, " +
                "EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        listQalifications.add("Python: Django.");
        listQalifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        listQalifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        listQalifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP," +
                " AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        listQalifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        listQalifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        listQalifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        listQalifications.add("Родной русский, английский \"upper intermediate\"");
        ListSection qualifications = new ListSection(listQalifications);
        List<Organization> organizationList= new ArrayList<>();
        resume.putContact(ContactType.MOBILE, " +7(921) 855-0482");
        resume.putContact(ContactType.SKYPE,"grigory.kislin");
        resume.putContact(ContactType.MAIL, "gkislin@yandex.ru");
        resume.putContact(ContactType.LINKEDIN, "Профиль LinkedIn");
        resume.putContact(ContactType.GITHUB,"Профиль GitHub");
        resume.putContact(ContactType.STACKOVERFLOW, "Профиль Stackoverflow");
        resume.putContact(ContactType.HOME_PAGE, "Домашняя страница");

        System.out.println(resume.toString());
        System.out.println(resume.getContacts(ContactType.MOBILE)) ;


    }

}
