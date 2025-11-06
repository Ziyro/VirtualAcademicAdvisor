package advisor.service;

import advisor.model.*;
import advisor.repo.CourseRepository;
import java.sql.SQLException;
import java.util.*;

/*
 * This is the main r logic for giving course advice.
 * I made it use the database repo instead of hardcoding anything.
 */
public class RuleBasedAdvisor implements AdvisorService 
{

    private final CourseRepository courseRepo;

    // connect the advisor to the course repository (so it can check prereqs)
       public RuleBasedAdvisor(CourseRepository courseRepo)
    {
      this.courseRepo = courseRepo;
    }

    @Override
    public List<Recommendation> recommendNextSemester(Student s) throws SQLException 
    {
        List<Recommendation> recs = new ArrayList<>();
        List<Course> all = courseRepo.findAll();
        List<String> done = s.getCompleted(); // list of completed courses for the student

        // go through every course and check if prerequisites are doneorfinished
        for (Course c : all) {
            if (done.contains(c.getCode())) continue; // skip if already completed

            boolean ok = true;
            for (String pre : c.getPrerequisites()) {
                if (!done.contains(pre.trim())) {
                    ok = false;
                    break;
                }
            }

            // if all prereqs are done, add it as a recommendation
            if (ok) {
                recs.add(new Recommendation(
                        c.getCode(),
                        "You can take " + c.getName() + " next semester."));
            }
        }

        // if nothing qualifies, show a message instead of an empty list
        if (recs.isEmpty()) {
            recs.add(new Recommendation("-", "No eligible courses found."));
        }

        return recs;
    }

    @Override
    public DegreePlan buildDraftPlan(Student s, List<Recommendation> recs) {
        // basically builds a simple plan object from the current recommendations
        List<String> list = new ArrayList<>();
        for (Recommendation r : recs) {
            if (!"-".equals(r.getCode())) list.add(r.getCode());
        }
        return new DegreePlan(list);
    }
}
