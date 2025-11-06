/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.model;

import java.util.ArrayList;
import java.util.List;

public class DegreePlan {
    private final List<String> courses;

    public DegreePlan(List<String> courses) {
        this.courses = courses == null ? new ArrayList<>() : new ArrayList<>(courses);
    }

    public List<String> getCourses() { return courses; }
}
