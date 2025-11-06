package advisor;

import advisor.gui.AdvisorFrame;
import advisor.gui.AppController;
import advisor.repo.*;
import advisor.service.*;
import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        try {
            // Initialize database
            Database.initialize();
            Connection conn = Database.getConnection();

            // Repositories
            CourseRepository courseRepo = new CourseRepository(conn);
            StudentRepository studentRepo = new StudentRepository(conn);

            // Advisor Service
            AdvisorService advisor = new RuleBasedAdvisor(courseRepo);

            // GUI Controller
            javax.swing.SwingUtilities.invokeLater(() -> {
                AppController controller = new AppController();
                AdvisorFrame frame = controller.getFrame();
                frame.setVisible(true);
            });

            System.out.println("Virtual Academic Advisor started successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Startup failed: " + e.getMessage());
        }
    }
}
