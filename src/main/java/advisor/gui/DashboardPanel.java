/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import javax.swing.*;
import java.awt.*;

//welcome screen 
//shows a message when program starts
public class DashboardPanel extends JPanel {
    public DashboardPanel() {
        //border layout so text is centered
        setLayout(new BorderLayout());

        //main welcome text (HTML for styling)
        JLabel label = new JLabel
        (
            "<html><center><h1>Welcome to the Virtual Academic Advisor</h1>" +
            "<p>Select a feature below to begin.</p></center></html>", SwingConstants.CENTER
        );
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        // add label to middle of screen
        add(label, BorderLayout.CENTER);
    }
}
