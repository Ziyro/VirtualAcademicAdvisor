package advisor.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Basic frame setup for the Virtual Academic Advisor app.
 * Only shows a placeholder window for now.
 */
public class AdvisorFrame extends JFrame {

    public AdvisorFrame() {
        setTitle("Virtual Academic Advisor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Advisor system loading...", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(label);
    }
}
