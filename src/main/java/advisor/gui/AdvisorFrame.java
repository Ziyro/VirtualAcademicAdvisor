/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import javax.swing.*;
import java.awt.*;

public class AdvisorFrame extends JFrame {

    private final AppController controller;
    private final CardLayout layout;
    private final JPanel cards;

    // Main window of the app that holds all GUI panels
    public AdvisorFrame(AppController controller) {
        this.controller = controller;

        setTitle("Virtual Academic Advisor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        layout = new CardLayout();
        cards = new JPanel(layout);
        getContentPane().setLayout(new BorderLayout());

        // Add panels for different sections
        DashboardPanel dashboard = new DashboardPanel();
        StudentsPanel students = new StudentsPanel(controller, controller.getStudentRepo());
        CoursesPanel courses = new CoursesPanel(controller.getCourseRepo());
        AdvicePanel advice = new AdvicePanel(controller.getAdvisor(), controller.getStudentRepo());

        // Add them to the card layout
        cards.add(dashboard, "DASH");
        cards.add(students, "STUDENTS");
        cards.add(courses, "COURSES");
        cards.add(advice, "ADVICE");

        add(cards, BorderLayout.CENTER);
        add(new NavPanel(this), BorderLayout.SOUTH);

        // Start at dashboard view
        layout.show(cards, "DASH");
    }

    // Switch between panels
    public void showCard(String name) {
        layout.show(cards, name);
    }
}
