/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import advisor.repo.CourseRepository;
import advisor.repo.StudentRepository;
import javax.swing.*;
import java.awt.*;
//main frame 4 app
//shows all main panels+switchng between them
public class AdvisorFrame extends JFrame 
{
    private final AppController controller;
    private final CardLayout layout;
    private final JPanel cards;

    public AdvisorFrame(AppController controller) 
    {
        this.controller = controller;
        setTitle("Virtual Academic Advisor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        layout = new CardLayout();
        cards = new JPanel(layout);
        getContentPane().setLayout(new BorderLayout());

        DashboardPanel dashboard = new DashboardPanel();
        StudentsPanel students = new StudentsPanel(controller, controller.getStudentRepo());
        CoursesPanel courses = new CoursesPanel(controller.getCourseRepo());
        AdvicePanel advice = new AdvicePanel(controller, controller.getStudentRepo(), controller.getCourseRepo());
        
         // add each panel
        cards.add(dashboard, "DASH");
        cards.add(students, "STUDENTS");
        cards.add(courses, "COURSES");
        cards.add(advice, "ADVICE");

        add(cards, BorderLayout.CENTER);
        add(new NavPanel(this), BorderLayout.SOUTH);

        layout.show(cards, "DASH");
    }

    public void showCard(String name) 
    {
        layout.show(cards, name); //switch between panels
    }
}
