package advisor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student with an ID, name, GPA, academic goal, 
 * and a list of completed course codes.
 */
public class Student {
    private String id;
    private String name;
    private double gpa;
    private String goal;
    private final List<String> completed = new ArrayList<>();

    // Constructor used for database loading (with goal)
    public Student(String id, String name, double gpa, String goal) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.goal = goal;
    }

    // Constructor for quick creation (goal optional)
    public Student(String id, String name, double gpa) {
        this(id, name, gpa, "");
    }

    // Getters and Setters
      public String getId() { return id; }
      public void setId(String id) { this.id = id; }

     public String getName() { return name; }
    public void setName(String name) { this.name = name; }

     public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

     public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }

    // Completed courses management
    public List<String> getCompleted() {
        return new ArrayList<>(completed);
    }

    public void addCompleted(String courseCode) {
        if (courseCode != null && !completed.contains(courseCode)) {
            completed.add(courseCode);
        }
    }

    public void removeCompleted(String courseCode) {
        completed.remove(courseCode);
    }

    public void clearCompleted() {
        completed.clear();
    }

    @Override
    public String toString() {
        return id + " - " + name + " (" + gpa + ")";
    }
}
