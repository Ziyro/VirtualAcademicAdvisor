package advisor.service;

import advisor.model.DegreePlan;
import advisor.model.Recommendation;
import advisor.model.Student;
import java.sql.SQLException;
import java.util.List;


 //dis is the interface for the academic advisor logic. it basically defines what any advisor implementation needs to do.
 
public interface AdvisorService {

    // figure out which courses a student can take next semester
    List<Recommendation> recommendNextSemester(Student s) throws SQLException;

    // build a rough degree plan based on those recommendations
    DegreePlan buildDraftPlan(Student s, List<Recommendation> recs);
}
