package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DateUtil.NOW;

/**
 * gkislin
 * 19.07.2016
 */
public class Organization {
    private final Link homePage;
    private List<Positions> positions;

    public Organization(String name, String url, Positions... positions) {
        this.homePage = new Link(name, url);
        this.positions = Arrays.asList(positions);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", positions=" + positions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return homePage.equals(that.homePage) && positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }

    public static class Positions {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public Positions(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this.startDate = DateUtil.of(startYear, startMonth);
            this.endDate = DateUtil.of(endYear, endMonth);
            this.title = title;
            this.description = description;
        }
        public Positions(int startYear, Month startMonth, String title, String description) {
            this.startDate = DateUtil.of(startYear, startMonth);
            this.endDate = NOW;
            this.title = title;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Positions positions = (Positions) o;
            return startDate.equals(positions.startDate) && endDate.equals(positions.endDate) && title.equals(positions.title) && Objects.equals(description, positions.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }
    }
}
