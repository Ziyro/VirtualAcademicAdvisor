package advisor.cui;

import advisor.model.DegreePlan;
import advisor.model.Recommendation;
import advisor.model.Student;
import advisor.repo.CourseRepository;
import advisor.repo.StudentRepository;
import advisor.service.AdvisorService;
import advisor.util.BackToMenu;
import advisor.util.ConsoleIO;
import advisor.util.Validation;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Menu {

     private final ConsoleIO io;
    private final AdvisorService advisor;
     private final CourseRepository courseRepo;
    private final StudentRepository studentRepo;

    public Menu(ConsoleIO io, AdvisorService advisor, CourseRepository courseRepo, StudentRepository studentRepo)
    {
        this.io = io;
         this.advisor = advisor;
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
    }

    public void start()
    {
        String choice;
        do {
            io.println("\nMain Menu:");
            io.println("1) List courses");
            io.println("2) Add student");
            io.println("3) Get study advice");
            io.println("4) Update completed courses");
            io.println("5) Export plan");
            io.println("X) Exit");

            choice = io.ask("Choice (1-5 or X): ").trim().toUpperCase();

            try {
                switch (choice) {
                    case "1" -> listCourses();
                    case "2" -> addStudent();
                    case "3" -> adviseStudent();
                    case "4" -> updateCompleted();
                    case "5" -> exportPlan();
                    case "X" -> io.ok("Goodbye!");
                    default -> io.warn("Unknown option. Please type 1-5 or X.");
                }
            } catch (BackToMenu e) {
                io.ok("Returning to main menu...");
            } catch (Exception e) {
                io.warn("Error: " + e.getMessage());
            }

        } while (!"X".equals(choice));
    }

    private void listCourses() throws SQLException {
        io.println("\n[COURSES]");
        io.println("Code | Points | Prerequisites");
        courseRepo.findAll().forEach(c -> io.println(" - " + c));
        io.pause();
    }

    private void addStudent() throws SQLException {
        io.println("\n[ADD STUDENT]");
        do {
            String id = Validation.requireDigits(io, "Student ID (digits only or B to go back): ");
            String name = Validation.requireName(io, "Full name (e.g. Bob Smith): ");
            double gpa = Validation.askDoubleInRange(io, "GPA (0.0-9.0): ", 0.0, 9.0);
            String goal = io.ask("Study goal (optional): ");

            Student s = new Student(id, name, gpa, goal);
            studentRepo.upsert(s);
            io.ok("Student saved successfully!");

        } while (io.yesNo("Add another student? (y/n): "));
    }

    private void adviseStudent() throws Exception {
        io.println("\n[GET ADVICE]");
        do {
            String id = Validation.requireDigits(io, "Student ID (digits only or B to go back): ");
            Optional<Student> maybe = studentRepo.findById(id);

            if (maybe.isEmpty()) {
                io.warn("Student not found. Please add them first.");
                continue;
            }

            Student s = maybe.get();
            io.println("Student: " + s.getName() + " | GPA: " + s.getGpa());

            List<Recommendation> recs = advisor.recommendNextSemester(s);
            if (recs.isEmpty()) {
                io.warn("No recommendations available.");
            } else {
                recs.forEach(r -> io.println(" - " + r.getCode() + ": " + r.message()));
                DegreePlan plan = advisor.buildDraftPlan(s, recs);
                studentRepo.savePlan(s.getId(), plan);
                io.ok("Plan saved to database.");
            }

        } while (io.yesNo("Advise another student? (y/n): "));
    }

    private void updateCompleted() throws SQLException {
        io.println("\n[UPDATE COMPLETED]");
        do {
            String id = Validation.requireDigits(io, "Student ID (digits only or B to go back): ");
            Optional<Student> maybe = studentRepo.findById(id);

            if (maybe.isEmpty()) {
                io.warn("No such student.");
                continue;
            }

            Student s = maybe.get();
            io.println("Current completed: " +
                    (s.getCompleted().isEmpty() ? "-" : String.join(", ", s.getCompleted())));

            String add = io.ask("Add courses (comma separated, Enter to skip): ");
            if (!add.isBlank()) {
                for (String c : add.split(",")) s.addCompleted(c.trim().toUpperCase());
            }

            String rem = io.ask("Remove courses (comma separated, Enter to skip): ");
if (!rem.isBlank()) {
    for (String c : rem.split(",")) s.removeCompleted(c.trim().toUpperCase());
}

            studentRepo.upsert(s);
            io.ok("Courses updated.");

        } while (io.yesNo("Modify another student? (y/n): "));
    }

    private void exportPlan() {
        io.println("\n[EXPORT PLAN]");
        io.warn("This feature can be linked to a file export later (not required for Derby DB).");
        io.pause();
    }
}
