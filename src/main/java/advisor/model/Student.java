
 //Represents a student and basic info (GPA, goal, etc).
 //Also stores completed courses in memory.
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
        this.goal = goal == null ? "" : goal;
    }

    public String getId() { return id; }
     public String getName() { return name; }
     public double getGpa() { return gpa; }
    public String getGoal() { return goal; }

     public void setName(String name) { this.name = name; }
    public void setGpa(double gpa) { this.gpa = gpa; }
     public void setGoal(String goal) { this.goal = goal; }

    // List of finished courses
    public List<String> getCompleted()
    { return completed; 
    }

    // Add a fnised course code
    public void addCompleted(String code) {
        code = code == null ? "" : code.trim().toUpperCase();
        if (!code.isEmpty() && !completed.contains(code)) completed.add(code);
    }

    // Remove completed course
    public void removeCompleted(String code) {
        if (code != null) completed.remove(code.trim().toUpperCase());
    }

    // Clear all completed courses
    public void clearCompleted() {
        completed.clear();
    }
}
