/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.model;

import java.util.ArrayList;
import java.util.List;

//list of courses dor degreeplan
public class DegreePlan
{
    private final List<String> courses;

    public DegreePlan(List<String> courses) 
    {
        //make sure list isn't null
        this.courses = courses == null ? new ArrayList<>() : new ArrayList<>(courses);
    }

    //returns the list of course codes
    public List<String> getCourses() { return courses; }
}
