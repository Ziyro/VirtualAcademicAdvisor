/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.service;

import advisor.model.*;
import advisor.repo.CourseRepository;
import java.sql.SQLException;
import java.util.*;

//advisor service to recommend courses
//checks prerequisites and suggests whatstudent can take next
public class RuleBasedAdvisor implements AdvisorService 
{

    private final CourseRepository courseRepo;

    public RuleBasedAdvisor(CourseRepository courseRepo) 
    {
        this.courseRepo = courseRepo;
    }

    @Override
    public List<Recommendation> recommendNextSemester(Student s) throws SQLException
    {
        //holds all generated recommendations
        List<Recommendation> recs = new ArrayList<>();
        List<Course> all = courseRepo.findAll();
        List<String> done = s.getCompleted(); 

        for (Course c : all) 
        {
            if (done.contains(c.getCode())) continue; 

            boolean ok = true;
            for (String pre : c.getPrerequisites()) 
            {
                if (!done.contains(pre.trim())) 
                {
                    ok = false;
                    break;
                }
            }
            if (ok) 
            {
                //add recommendation for eligible course
                recs.add(new Recommendation(
                        c.getCode(),
                        "You can take " + c.getName() + " next semester."));
            }
        }
        //if none found
        if (recs.isEmpty()) 
        {
            recs.add(new Recommendation("-", "No eligible courses found."));
        }
        return recs;
    }

    @Override
    public DegreePlan buildDraftPlan(Student s, List<Recommendation> recs) 
    {
        //collect all valid recommended course codes
        List<String> list = new ArrayList<>();
        for (Recommendation r : recs) {
            if (!"-".equals(r.getCode())) list.add(r.getCode());
        }
        return new DegreePlan(list);
    }
}