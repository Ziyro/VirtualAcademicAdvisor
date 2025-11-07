/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import javax.swing.*;
import java.awt.*;

//dash screen
public class DashboardPanel extends JPanel 
{
    public DashboardPanel() 
    {
       
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250)); //soft background tone

      
        JLabel label = new JLabel(
            "<html><center>" +
            "<h1 style='font-size:30px; color:#283C6E; border-bottom:2px solid #AAAAAA; padding-bottom:6px;'>"
            + "Welcome to the Virtual Academic Advisor" + "</h1>" +
            "<p style='font-size:18px; color:#555555;'>Select a feature below to begin.</p>" +
            "</center></html>",
            SwingConstants.CENTER
        );

        
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        add(label, BorderLayout.CENTER);
    }
}
