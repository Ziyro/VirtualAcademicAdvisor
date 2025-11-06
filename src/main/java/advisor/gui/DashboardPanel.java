
//   home panel showing the app title+intro
 

package advisor.gui;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    public DashboardPanel() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Welcome to the Virtual Academic Advisor", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 26));
        add(label, BorderLayout.CENTER);
    }
}
