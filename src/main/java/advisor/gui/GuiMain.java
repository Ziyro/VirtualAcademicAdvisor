/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package advisor.gui;

import advisor.repo.*;
import advisor.service.*;
import javax.swing.*;

// main entry pointof app
//sets everything up and launches main window
public class GuiMain {
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        { 
            try 
            {
                //connect to database first
                Database.initialize();
                var conn = Database.getConnection();

                //setup repos and advisor service
                CourseRepository courseRepo = new CourseRepository(conn);
                StudentRepository studentRepo = new StudentRepository(conn);
                AdvisorService advisor = new RuleBasedAdvisor(courseRepo);

                //make the controller +show  window
                AppController controller = new AppController(advisor, courseRepo, studentRepo);
                controller.getFrame().setVisible(true);
            } catch (Exception e) 
            {
                //  incase something goes wrong
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error starting application: " + e.getMessage());
            }
        });
    }
}
