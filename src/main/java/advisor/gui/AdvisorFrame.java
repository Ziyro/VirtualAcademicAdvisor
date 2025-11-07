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
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //prevent auto close, we add confirm exit
        setSize(1200, 800);
        setLocationRelativeTo(null);

        layout = new CardLayout();
        cards = new JPanel(layout);
        getContentPane().setLayout(new BorderLayout());

        //create each main panel
        DashboardPanel dashboard = new DashboardPanel();
        StudentsPanel students = new StudentsPanel(controller, controller.getStudentRepo());
        CoursesPanel courses = new CoursesPanel(controller.getCourseRepo());
        AdvicePanel advice = new AdvicePanel(controller, controller.getStudentRepo(), controller.getCourseRepo());
        
        //add each panel
        cards.add(dashboard, "DASH");
        cards.add(students, "STUDENTS");
        cards.add(courses, "COURSES");
        cards.add(advice, "ADVICE");

        add(cards, BorderLayout.CENTER);
        add(new NavPanel(this), BorderLayout.SOUTH);

        layout.show(cards, "DASH");

        //confirm before exit (prevents accidental close)
        addWindowListener(new java.awt.event.WindowAdapter() 
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) 
            {
                int result = JOptionPane.showConfirmDialog(
                    AdvisorFrame.this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                if (result == JOptionPane.YES_OPTION) 
                {
                    System.exit(0); //close only if user confirms
                }
            }
        });
    }

    public void showCard(String name) 
    {
        layout.show(cards, name); //switch between panels
    }
}
