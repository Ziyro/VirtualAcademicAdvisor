/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package advisor.cui;

import advisor.model.*;
import advisor.repo.*;
import advisor.service.*;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class App {

    private final AdvisorService advisor;
    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final Scanner sc = new Scanner(System.in);

    public App(AdvisorService advisor, StudentRepository studentRepo, CourseRepository courseRepo) {
        this.advisor = advisor;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }

    public void run() {
        System.out.println("=== Virtual Academic Advisor (CUI) ===");
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. List Courses");
            System.out.println("2. Add Student");
            System.out.println("3. Get Study Advice");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1" -> listCourses();
                case "2" -> addStudent();
                case "3" -> adviseStudent();
                case "4" -> { System.out.println("Goodbye!"); return; }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void listCourses() {
        try {
            List<Course> list = courseRepo.findAll();
            System.out.println("\nAvailable Courses:");
            list.forEach(c -> System.out.println("- " + c.getCode() + " (" + c.getPoints() + " points)"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addStudent() {
        try {
            System.out.print("Enter ID: ");
            String id = sc.nextLine().trim();
            if (!id.matches("\\d+")) { System.out.println("ID must be digits only."); return; }

            System.out.print("Enter Name: ");
            String name = sc.nextLine().trim();
            System.out.print("Enter GPA (0–9): ");
            double gpa = Double.parseDouble(sc.nextLine().trim());
            System.out.print("Enter Goal (optional): ");
            String goal = sc.nextLine().trim();

            studentRepo.upsert(new Student(id, name, gpa, goal));
            System.out.println("Student saved successfully.");
        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    private void adviseStudent() {
        try {
            System.out.print("Enter Student ID: ");
            String id = sc.nextLine().trim();
            Student s = studentRepo.findById(id).orElseThrow(() -> new Exception("Student not found."));
            List<Recommendation> recs = advisor.recommendNextSemester(s);
            System.out.println("\nStudy Advice for " + s.getName() + ":");
            for (Recommendation r : recs) {
                System.out.println("- " + r.getCode() + ": " + r.message());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            Database.initialize();
            Connection conn = Database.getConnection();
            CourseRepository courseRepo = new CourseRepository(conn);
            StudentRepository studentRepo = new StudentRepository(conn);
            AdvisorService advisor = new RuleBasedAdvisor(courseRepo);
            new App(advisor, studentRepo, courseRepo).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
