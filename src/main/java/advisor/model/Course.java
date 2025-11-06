/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//rpresnts course with+code, name, points, and prequistes
//used for displaying and recommending courses
public class Course 
{
     private String code;
    private String name;
     private int points;
    private List<String> prerequisites;

    //loading from DB with prereq list
    public Course(String code, String name, int points, List<String> prereq) 
    {
        this.code = code;
        this.name = name;
        this.points = points;
        this.prerequisites = prereq == null ? new ArrayList<>() : new ArrayList<>(prereq);
    }

    
    public Course(String code, String name, int points, String prereqString) 
    {
        this.code = code;
        this.name = name;
        this.points = points;
        if (prereqString == null || prereqString.isBlank()) 
        {
            this.prerequisites = new ArrayList<>();
        } else 
        {
            this.prerequisites = Arrays.asList(prereqString.split(","));
        }
    }
       //getters for course data
    public String getCode() { return code; }
     public String getName() { return name; }
    public int getPoints() { return points; }
     public List<String> getPrerequisites() { return prerequisites; }

    @Override
    public String toString() 
    {
        return code + " - " + name + " (" + points + " pts)";
    }
}
