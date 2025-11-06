package advisor.gui;

import advisor.repo.*;
import advisor.service.*;
import javax.swing.*;

public class GuiMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Initialize the database (creates tables if needed)
                Database.initialize();

                // Get the existing Derby connection from Database.java
                var conn = Database.getConnection();

                // Set up repositories and advisor logic
                CourseRepository courseRepo = new CourseRepository(conn);
                StudentRepository studentRepo = new StudentRepository(conn);
                AdvisorService advisor = new RuleBasedAdvisor(courseRepo);

                // Pass everything into the main controller
                AppController controller = new AppController(advisor, courseRepo, studentRepo);

                // Launch the main GUI window
                controller.getFrame().setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error starting application: " + e.getMessage());
            }
        });
    }
}
