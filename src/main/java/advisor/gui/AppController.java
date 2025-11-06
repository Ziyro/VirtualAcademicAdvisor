/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import advisor.repo.CourseRepository;
import advisor.repo.StudentRepository;
import advisor.service.AdvisorService;

public class AppController {

    private final AdvisorService advisor;
    private final CourseRepository courseRepo;
    private final StudentRepository studentRepo;
    private final AdvisorFrame frame;

    // Main controller that connects all parts of the GUI together
    public AppController(AdvisorService advisor, CourseRepository courseRepo, StudentRepository studentRepo) {
        this.advisor = advisor;
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;

        // Create and link the main frame
        this.frame = new AdvisorFrame(this);
    }

    // Getters used by the GUI panels
    public AdvisorFrame getFrame() {
        return frame;
    }

    public AdvisorService getAdvisor() {
        return advisor;
    }

    public CourseRepository getCourseRepo() {
        return courseRepo;
    }

    public StudentRepository getStudentRepo() {
        return studentRepo;
    }
}
