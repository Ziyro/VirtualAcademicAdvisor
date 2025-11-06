/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import advisor.repo.CourseRepository;
import advisor.repo.StudentRepository;
import advisor.service.AdvisorService;

//main controller,connects everything together
//references to repos, services,+main frame
public class AppController 
{
    private final AdvisorService advisor;
    private final CourseRepository courseRepo;
    private final StudentRepository studentRepo;
    private final AdvisorFrame frame;

    public AppController(AdvisorService advisor, CourseRepository courseRepo, StudentRepository studentRepo) 
    {
        this.advisor = advisor;
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
        this.frame = new AdvisorFrame(this); // main window
    }

    //getters so other classes can access
    public AdvisorFrame getFrame() { return frame; 
    }
    public AdvisorService getAdvisorService() { return advisor; 
    }
    public CourseRepository getCourseRepo() { return courseRepo; 
    }
    public StudentRepository getStudentRepo() { return studentRepo; 
    }
}
