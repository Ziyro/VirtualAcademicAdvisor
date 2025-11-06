/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.service;

import advisor.model.DegreePlan;
import advisor.model.Recommendation;
import advisor.model.Student;
import java.sql.SQLException;
import java.util.List;

//defines what an advisor service should do
//used for generating course advice and building degree plans
public interface AdvisorService {
    //returns course recommendations for a student
    List<Recommendation> recommendNextSemester(Student s) throws SQLException;

    //makes draft degree plan based on recomendatins
    DegreePlan buildDraftPlan(Student s, List<Recommendation> recs);
}
