package advisor.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Displays welcome screen.
 */
public class DashboardPanel extends JPanel {
    public DashboardPanel() 
    {
        setLayout(new BorderLayout());
        JLabel label = new JLabel(
                "<html><center><h1>Welcome to the Virtual Academic Advisor</h1>" +
                "<p>Use the buttons below to navigate between panels.</p></center></html>",
                SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        add(label, BorderLayout.CENTER);
    }
}
