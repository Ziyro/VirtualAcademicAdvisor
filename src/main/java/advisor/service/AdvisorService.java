package advisor.service;

import advisor.model.Student;

/**
 * Interface for future advisor logic.
 */
public interface AdvisorService {
    void recommendFor(Student s);
}
