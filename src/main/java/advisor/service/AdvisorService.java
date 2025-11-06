
 //for advising logic.
 // GUI and CUI parts call this
package advisor.service;

import advisor.model.DegreePlan;
import advisor.model.Recommendation;
import advisor.model.Student;
import java.sql.SQLException;
import java.util.List;

public interface AdvisorService {
    // returns course recommendations for a student
    List<Recommendation> recommendNextSemester(Student s) throws SQLException;

    // builds plan from the recommendations
    DegreePlan buildDraftPlan(Student s, List<Recommendation> recs);
}
