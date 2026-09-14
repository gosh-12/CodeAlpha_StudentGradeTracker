package com.codealpha.gradetracker;

import com.codealpha.gradetracker.model.Student;
import com.codealpha.gradetracker.service.GradeService;
import com.codealpha.gradetracker.validation.InputValidator;

import java.util.Optional;
import java.util.Scanner;

public class solution {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final GradeService SERVICE = new GradeService();

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   CodeAlpha - Student Grade Tracker");
        System.out.println("========================================");

        boolean running = true;

        while (running) {

            printMenu();

            String choice = SCANNER.nextLine().trim();

            switch (choice) {

                case "1" -> addStudent();

                case "2" -> viewStudents();

                case "3" -> searchStudent();

                case "4" -> summaryReport();

                case "0" -> {
                    running = false;
                    System.out.println("\nExiting. Goodbye!");
                }

                default -> System.out.println(
                        "Invalid option. Please try again."
                );
            }
        }
    }

    private static void printMenu() {

        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Summary Report");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addStudent() {

        System.out.println("\n--- Add Student ---");

        String id = readNonEmpty("Enter student ID: ");

        if (SERVICE.findById(id).isPresent()) {
            System.out.println("A student with this ID already exists.");
            return;
        }

        String name = readNonEmpty("Enter student name: ");

        Student student = new Student(id, name);

        int subjectCount = readInt(
                "How many subjects? ",
                1,
                20
        );

        for (int i = 1; i <= subjectCount; i++) {

            String subject = readNonEmpty(
                    "Subject " + i + " name: "
            );

            int mark = readMark(
                    "Marks for " + subject + " (0-100): "
            );

            student.addMark(subject, mark);
        }

        SERVICE.addStudent(student);

        System.out.println("\nStudent added successfully!");

        printStudentRow(student);
    }

    private static void viewStudents() {

        System.out.println("\n--- All Students ---");

        if (SERVICE.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student student : SERVICE.getAllStudents()) {
            printStudentRow(student);
        }
    }

    private static void searchStudent() {

        System.out.println("\n--- Search Student ---");

        if (SERVICE.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        String id = readNonEmpty(
                "Enter student ID to search: "
        );

        Optional<Student> match = SERVICE.findById(id);

        if (match.isEmpty()) {
            System.out.println(
                    "No student found with ID '" + id + "'."
            );
            return;
        }

        printStudentDetails(match.get());
    }

    private static void summaryReport() {

        System.out.println("\n--- Summary Report ---");

        if (SERVICE.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println(
                "Total students: " + SERVICE.count()
        );

        System.out.printf(
                "Class average: %.2f%n",
                SERVICE.classAverage()
        );

        SERVICE.highestAverage().ifPresent(student ->
                System.out.printf(
                        "Highest average: %s (%s) - %.2f%n",
                        student.getName(),
                        student.getId(),
                        student.getAverage()
                )
        );

        SERVICE.lowestAverage().ifPresent(student ->
                System.out.printf(
                        "Lowest average: %s (%s) - %.2f%n",
                        student.getName(),
                        student.getId(),
                        student.getAverage()
                )
        );
    }

    private static void printStudentRow(Student student) {

        System.out.printf(
                "ID: %-6s | Name: %-15s | Total: %-4d | Avg: %6.2f | Grade: %s | %s%n",
                student.getId(),
                student.getName(),
                student.getTotal(),
                student.getAverage(),
                student.getGrade(),
                student.getStatus()
        );
    }

    private static void printStudentDetails(Student student) {

        System.out.println("\nID    : " + student.getId());
        System.out.println("Name  : " + student.getName());

        System.out.println("Marks:");

        student.getMarks().forEach((subject, mark) ->
                System.out.printf(
                        "   - %-15s : %d%n",
                        subject,
                        mark
                )
        );

        System.out.println("Total : " + student.getTotal());

        System.out.printf(
                "Average: %.2f%n",
                student.getAverage()
        );

        System.out.println("Grade : " + student.getGrade());
        System.out.println("Status: " + student.getStatus());
    }

    private static String readNonEmpty(String prompt) {

        while (true) {

            System.out.print(prompt);

            String value = SCANNER.nextLine();

            if (InputValidator.isNonEmpty(value)) {
                return value.trim();
            }

            
            System.out.println(
                    "Value cannot be empty. Please try again."
            );
        }
    }

    private static int readInt(
            String prompt,
            int min,
            int max) {

        while (true) {

            System.out.print(prompt);

            String raw = SCANNER.nextLine().trim();

            try {

                int value = Integer.parseInt(raw);

                if (value < min || value > max) {

                    System.out.printf(
                            "Please enter a number between %d and %d.%n",
                            min,
                            max
                    );

                    continue;
                }

                return value;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }

    private static int readMark(String prompt) {

        return readInt(
                prompt,
                InputValidator.MIN_MARK,
                InputValidator.MAX_MARK
        );
    }
}