
  //Main advising logic
 // Check course prerequisites and recommends what the studnet can take next.
package advisor.service;

import advisor.model.*;
import advisor.repo.CourseRepository;
import java.sql.SQLException;
import java.util.*;

public class RuleBasedAdvisor implements AdvisorService {

    private final CourseRepository courseRepo;

    // repo is passed in from the app so it can access the DB
    public RuleBasedAdvisor(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    // figure out which courses the student is eligible to take
    @Override
    public List<Recommendation> recommendNextSemester(Student s) throws SQLException {
        List<Recommendation> recs = new ArrayList<>();
        List<Course> all = courseRepo.findAll(); // all available courses
        List<String> done = s.getCompleted();    // already finished courses

        for (Course c : all) {
            if (done.contains(c.getCode())) continue; // skip if done already

            boolean ok = true;
            for (String pre : c.getPrerequisites()) {
                if (!done.contains(pre.trim())) {
                    ok = false;
                    break;
                }
            }

            // only add if all prereqs are met
            if (ok) {
                recs.add(new Recommendation(
                        c.getCode(),
                        "You can take " + c.getName() + " next semester."));
            }
        }

        //handle where nothing is available
        if (recs.isEmpty()) {
            recs.add(new Recommendation("-", "No eligible courses found."));
        }

        return recs;
    }

    //build a degree plan from the recommended list
    @Override
    public DegreePlan buildDraftPlan(Student s, List<Recommendation> recs) {
        List<String> list = new ArrayList<>();
        for (Recommendation r : recs) {
            if (!"-".equals(r.getCode())) list.add(r.getCode());
        }
        return new DegreePlan(list);
    }
}
