package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();

    }

    //@Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        storage = Config.get().getStorage();
//    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        if (uuid == null) {
            PrintWriter writer = response.getWriter();
            writer.write(
                    "<html>" +
                            "<head>" +
                                "<title> Resumes </title>" +
                            "</head>" +
                            "<body>" +
                            "<center>" +
                                "<table border =\"1\" width=\"640\" height=\"480\"> " +
                                "<tr>" +
                                    "<td> name </td>" +
                                    "<td> uuid </td>" +
                                "</tr>");

//            for (Resume r : storage.getAllSorted()) {
//                    writer.write(
//                            "<tr>" +
//                                    "<td>" + r.getFullName() + "</td>" +
//                                    "<td>" + r.getUuid() + "</td>" +
//                                 "</tr>");
//
//                }
                writer.write(
                        "</table>" +
                                "</body>" +
                                "</center>" +
                                "</html>"
                );


        } else {

        }

    }
}
