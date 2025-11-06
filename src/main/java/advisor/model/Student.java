/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.model;
//handles student info like name, gpa, goal, and completed courses
//also supports adding/removing courses they've finished
import java.util.ArrayList;
import java.util.List;

public class Student 
{
    private final String id;
    private String name;
    private double gpa;
    private String goal;

   
    private final List<String> completed = new ArrayList<>();

    public Student(String id, String name, double gpa, String goal)
    {
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

    // completed courses (in-memory only in this version)
    public List<String> getCompleted() { return completed; }
    public void addCompleted(String code) {
        code = code == null ? "" : code.trim().toUpperCase();
        if (!code.isEmpty() && !completed.contains(code)) completed.add(code);
    }
    public void removeCompleted(String code) {
        if (code != null) completed.remove(code.trim().toUpperCase());
    }

    public void clearCompleted() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
