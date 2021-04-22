package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateAdapter;
import ru.javawebinar.basejava.util.LocalDateAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            // TODO implements sections

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());

            SectionType[] values = SectionType.values();
            if (!sections.isEmpty()) {
                for (int i = 0; i < values.length; i++) {
                    dos.writeUTF(values[i].name());
                    writeSections(sections.get(values[i]), dos);

                }
            }

        }
    }

    //
    private void writeSections(Section value, DataOutputStream dos) throws IOException {
        if (value instanceof TextSection) {
            dos.writeUTF(((TextSection) value).getContent());
        } else if (value instanceof ListSection) {
            if (value != null) {
                List<String> items = ((ListSection) value).getItems();
                dos.writeInt(items.size());
                writeLists(items, dos);
            }

        } else if (value instanceof OrganizationSection) {
            if (value != null) {
                writeOrganizations(((OrganizationSection) value).getOrganizations(), dos);
            }


        }

    }

    private void writeOrganizations(List<Organization> organizations, DataOutputStream dos) throws IOException {
        if (organizations != null) {
            dos.writeInt(organizations.size());
            for (Organization or : organizations) {

                writeLink(or.getHomePage(), dos);
                writeListsPositsons(or.getPositions(), dos);
            }
        }

    }

    private void writeListsPositsons(List<Organization.Position> positions, DataOutputStream dos) throws IOException {
        dos.writeInt(positions.size());
        for (Organization.Position op : positions) {

            try {
                dos.writeUTF(DateAdapter.marshal(op.getStartDate()));
                dos.writeUTF(DateAdapter.marshal(op.getEndDate()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            dos.writeUTF(op.getTitle());
            if (op.getDescription() == null) {
                dos.writeUTF("do not Description");
            } else {
                dos.writeUTF(op.getDescription());
            }


        }
    }

    private void writeLink(Link homePage, DataOutputStream dos) throws IOException {
        dos.writeUTF(homePage.getName());
        if (homePage.getUrl() == null) {
            dos.writeUTF("do not URL");
        } else {
            dos.writeUTF(homePage.getUrl());
        }

    }

    //
    private void writeLists(List<String> items, DataOutputStream dos) throws IOException {
        for (int i = 0; i < items.size(); i++) {
            dos.writeUTF(items.get(i));
        }
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            // TODO implements sections
            size = dis.readInt();
            System.out.println(size);
            for (int i = 0; i < size; i++) {
                String s = dis.readUTF();
                switch (s) {
                    case "PERSONAL":
                        resume.addSection(SectionType.PERSONAL, new TextSection(dis.readUTF()));
                        break;
                    case "OBJECTIVE":
                        resume.addSection(SectionType.OBJECTIVE, new TextSection(dis.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(readList(dis)));
                        break;
                    case "QUALIFICATIONS":
                        resume.addSection(SectionType.QUALIFICATIONS, new ListSection((readList(dis))));
                        break;
                    case "EXPERIENCE":
                        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(readOrganizationList(dis)));
                        break;
                    case "EDUCATION":
                        resume.addSection(SectionType.EDUCATION, new OrganizationSection(readOrganizationList(dis)));
                        break;
                }
            }
            return resume;
        }
    }

    private List<Organization> readOrganizationList(DataInputStream dis) throws IOException {
        List<Organization> organizationList = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            organizationList.add(readOrganization(dis));
        }
        return organizationList;
    }

    private Organization readOrganization(DataInputStream dis) throws IOException {
        return new Organization(readLink(dis), readPositions(dis));
    }

    private Link readLink(DataInputStream dis) throws IOException {

        return new Link(dis.readUTF(), readURL(dis));
    }

    private String readURL(DataInputStream dis) throws IOException {
        String s = dis.readUTF();
        if (s.equals("do not URL")){
            return null;
        } else {
            return s;
        }

    }

    private List<Organization.Position> readPositions(DataInputStream dis) throws IOException {
        List<Organization.Position> positions = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            try {
                positions.add(new Organization.Position(
                        DateAdapter.unmarshal(dis.readUTF()),
                        DateAdapter.unmarshal(dis.readUTF()),
                        dis.readUTF(),
                        readDescription(dis)

                ));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return positions;
    }

    private String readDescription(DataInputStream dis) throws IOException {
        String s = dis.readUTF();
        if(s.equals("do not Description")){
            return null;
        } else {
            return s;
        }

    }

    private List<String> readList(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(dis.readUTF());
        }
        return list;
    }
}
