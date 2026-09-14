package com.codealpha.gradetracker.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Student {

    private final String id;
    private String name;
    private final Map<String, Integer> marks;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.marks = new LinkedHashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getMarks() {
        return Collections.unmodifiableMap(marks);
    }

    public void addMark(String subject, int mark) {
        marks.put(subject, mark);
    }

    public int getTotal() {
        int total = 0;

        for (int mark : marks.values()) {
            total += mark;
        }

        return total;
    }

    public double getAverage() {
        if (marks.isEmpty()) {
            return 0.0;
        }

        return (double) getTotal() / marks.size();
    }

    public String getGrade() {
        double average = getAverage();

        if (average >= 75) {
            return "A";
        }

        if (average >= 60) {
            return "B";
        }

        if (average >= 40) {
            return "C";
        }

        return "F";
    }

    public String getStatus() {
        return getAverage() >= 40 ? "PASS" : "FAIL";
    }
}