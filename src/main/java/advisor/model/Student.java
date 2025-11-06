package advisor.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String id;
    private String name;
    private double gpa;
    private String goal;
    private final List<String> completed = new ArrayList<>();

    public Student(String id, String name, double gpa, String goal) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.goal = goal;
    }

    // Constructor without goal (for backward compatibility)
    public Student(String id, String name, double gpa) {
        this(id, name, gpa, "");
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getGpa() { return gpa; }
    public String getGoal() { return goal; }

    public void setName(String name) { this.name = name; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public void setGoal(String goal) { this.goal = goal; }

    public List<String> getCompleted() { return completed; }
    public void addCompleted(String code) { if (!completed.contains(code)) completed.add(code); }
    public void removeCompleted(String code) { completed.remove(code); }
    public void clearCompleted() { completed.clear(); }

    @Override
    public String toString() {
        return name + " (" + id + ") GPA: " + gpa + " Goal: " + goal;
    }
}
