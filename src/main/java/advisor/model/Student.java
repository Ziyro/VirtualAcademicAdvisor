/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// Represents a student with ID, name, GPA, goal and completed courses
 package advisor.model;

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
    public List<String> getCompleted() { return completed; }

    public void setName(String name) { this.name = name; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public void setGoal(String goal) { this.goal = goal; }

    public void addCompleted(String code) 
    {
        code = code == null ? "" : code.trim().toUpperCase();
        if (!code.isEmpty() && !completed.contains(code)) completed.add(code);
    }

    public void removeCompleted(String code) 
    {
        if (code != null) completed.remove(code.trim().toUpperCase());
    }

    public void clearCompleted() 
    {
        completed.clear();
    }
}
