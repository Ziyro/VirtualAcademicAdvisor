/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//bottom navbar for switching btween screens
public class NavPanel extends JPanel implements ActionListener {
    private final AdvisorFrame frame;

    public NavPanel(AdvisorFrame frame) 
    {
        this.frame = frame;
        //setup layout and spacing for nav buttons
        setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        //add main nav buttons
        add(make("Dashboard", "DASH"));
        add(make("Students", "STUDENTS"));
        add(make("Courses", "COURSES"));
        add(make("Get Advice", "ADVICE"));
        add(make("Exit", "EXIT"));
    }

    private JButton make(String text, String cmd)
    {
        //to create and style each button
        JButton b = new JButton(text);
        b.setActionCommand(cmd);
        b.addActionListener(this);
        b.setPreferredSize(new Dimension(180, 50));
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        //handle button clicks
        if ("EXIT".equals(e.getActionCommand())) System.exit(0);
        else frame.showCard(e.getActionCommand());
    }
}
