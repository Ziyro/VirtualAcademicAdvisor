package advisor.service;

import advisor.model.Course;
import advisor.model.DegreePlan;
import advisor.model.Recommendation;
import advisor.model.Student;
import advisor.repo.CourseRepository;
import java.sql.SQLException;
import java.util.*;

/**
 * Generates course recommendations based on completed prerequisites.
 * Uses the CourseRepository to fetch get data from the database.
 */
public class RuleBasedAdvisor implements AdvisorService
{

    private final CourseRepository courseRepo;

    public RuleBasedAdvisor(CourseRepository courseRepo) 
    {
        this.courseRepo = courseRepo;
    }

    /**
     * Recommend eligible courses for the student
     *only allows course if prerequis hav been met
     */
    @Override
    public List<Recommendation> recommendNextSemester(Student s) throws SQLException 
    {
        List<Recommendation> recs = new ArrayList<>();
        List<Course> allCourses = courseRepo.findAll();
        List<String> completed = s.getCompleted();

        for (Course c : allCourses) {
            if (completed.contains(c.getCode())) continue; // skip completed courses

            boolean eligible = true;
            for (String pre : c.getPrerequisites())
            {
                if (!pre.isBlank() && !completed.contains(pre.trim())) 
                {
                    eligible = false;
                    break;
                }
            }

            if (eligible) 
            {
                recs.add(new Recommendation(
                        c.getCode(),
                        "You can take " + c.getName() + " next semester."
                ));
            }
        }

        if (recs.isEmpty()) 
        {
            recs.add(new Recommendation("-", "No eligible courses found."));
        }

        return recs;
    }

    /**
     * Build a draft degree plan from recommendations.
     */
    @Override
    public DegreePlan buildDraftPlan(Student s, List<Recommendation> recs) 
    {
        List<String> planCourses = new ArrayList<>();
        for (Recommendation r : recs) {
            if (!r.getCode().equals("-")) {
                planCourses.add(r.getCode());
            }
        }
        return new DegreePlan(planCourses);
    }
}
