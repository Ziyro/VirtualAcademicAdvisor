package advisor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Navigation bar at the bottom of the app.
 * Currently supports switching between Dashboard and Advice panels.
 */
public class NavPanel extends JPanel implements ActionListener {
    private final AdvisorFrame frame;

    public NavPanel(AdvisorFrame frame) {
        this.frame = frame;
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(makeButton("Dashboard", "DASHBOARD"));
        add(makeButton("Advice", "ADVICE"));
        add(makeButton("Exit", "EXIT"));
    }

    private JButton makeButton(String text, String cmd) {
        JButton btn = new JButton(text);
        btn.setActionCommand(cmd);
        btn.addActionListener(this);
        btn.setPreferredSize(new Dimension(160, 45));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("EXIT".equals(e.getActionCommand())) System.exit(0);
        else frame.showCard(e.getActionCommand());
    }
}
