package com.codealpha.gradetracker.service;

import com.codealpha.gradetracker.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GradeService {

    private final List<Student> students = new ArrayList<>();

    public boolean addStudent(Student student) {
        if (findById(student.getId()).isPresent()) {
            return false;
        }

        students.add(student);
        return true;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Optional<Student> findById(String id) {

        if (id == null) {
            return Optional.empty();
        }

        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(id.trim())) {
                return Optional.of(student);
            }
        }

        return Optional.empty();
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }

    public int count() {
        return students.size();
    }

    public Optional<Student> highestAverage() {

        Student best = null;

        for (Student student : students) {
            if (best == null ||
                    student.getAverage() > best.getAverage()) {
                best = student;
            }
        }

        return Optional.ofNullable(best);
    }

    public Optional<Student> lowestAverage() {

        Student worst = null;

        for (Student student : students) {
            if (worst == null ||
                    student.getAverage() < worst.getAverage()) {
                worst = student;
            }
        }

        return Optional.ofNullable(worst);
    }

    public double classAverage() {

        if (students.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;

        for (Student student : students) {
            sum += student.getAverage();
        }

        return sum / students.size();
    }
}
