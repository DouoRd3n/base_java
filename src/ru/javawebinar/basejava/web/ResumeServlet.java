package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values() ) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            switch (type){
                case PERSONAL:
                case OBJECTIVE:
                    r.addSection(type, new TextSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    r.addSection(type, new ListSection(value.split("\\n")));
                    break;
                case EXPERIENCE:
                case EDUCATION :
                    List<Organization> orgs = new ArrayList<>();
                    String[] urls = request.getParameterValues(type.name() + "url");
                    for (int i = 0; i < values.length ; i++) {
                        String name = values[i];

                            List<Organization.Position> positions = new ArrayList<>();
                            String pfx = type.name() + i;
                            String[] starDate = request.getParameterValues(pfx + "startDate");
                            String[] endDate = request.getParameterValues(pfx + "endDate");
                            String[] title = request.getParameterValues(pfx + "title");
                            String[] description = request.getParameterValues(pfx + "description");
                            for (int j = 0; j < title.length; j++) {
                                if (HtmlUtil.isEmpty(name)) {
                                positions.add(new Organization.Position(DateUtil.parse(starDate[j]), DateUtil.parse(endDate[j]), title[j], description[j]));
                            }
                        }
                        orgs.add(new Organization(new Link(name, urls[i]),positions));
                    }

            }

        }
        storage.update(r);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : new SectionType[]{SectionType.EXPERIENCE, SectionType.EDUCATION}) {
                    OrganizationSection section = (OrganizationSection) r.getSection(type);
                    List<Organization> emptyFirstOrganizations = new ArrayList<>();
                    emptyFirstOrganizations.add(Organization.EMPTY);
                    if (section != null) {
                        for (Organization org : section.getOrganizations()) {
                            List<Organization.Position> emptyFirstPositions = new ArrayList<>();
                            emptyFirstPositions.add(Organization.Position.EMPTY);
                            emptyFirstPositions.addAll(org.getPositions());
                            emptyFirstOrganizations.add(new Organization(org.getHomePage(), emptyFirstPositions));
                        }
                    }
                    r.addSection(type, new OrganizationSection(emptyFirstOrganizations));
                }
                
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
