/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package advisor;

import advisor.gui.AppController;
import advisor.repo.*;
import advisor.service.*;
import javax.swing.*;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Setup database connection and initialize tables if needed
                Database.initialize();
                var conn = Database.getConnection();

                // Create repositories for courses and students
                CourseRepository courseRepo = new CourseRepository(conn);
                StudentRepository studentRepo = new StudentRepository(conn);

                // The main advisor logic used for recommendations
                AdvisorService advisor = new RuleBasedAdvisor(courseRepo);

                // Controller links everything together for the GUI
                AppController controller = new AppController(advisor, courseRepo, studentRepo);

                // Show main application window
                controller.getFrame().setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error starting application: " + e.getMessage());
            }
        });
    }
}
