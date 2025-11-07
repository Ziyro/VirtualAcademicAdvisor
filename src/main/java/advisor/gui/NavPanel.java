/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//bottom nav bar panel
//switch between dashboard, students, courses, advice
public class NavPanel extends JPanel implements ActionListener 
{
    private final AdvisorFrame frame;

    public NavPanel(AdvisorFrame frame) 
    {
        this.frame = frame;

        //layout + style
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        setBackground(new Color(245, 247, 250));

        //create buttons (same as before)
        JButton dashBtn = makeButton("Dashboard", "DASH");
        JButton studentsBtn = makeButton("Students", "STUDENTS");
        JButton coursesBtn = makeButton("Courses", "COURSES");
        JButton adviceBtn = makeButton("Advice", "ADVICE");
        JButton exitBtn = makeButton("Exit", "EXIT");

        //add them all (no changes)
        add(dashBtn);
        add(studentsBtn);
        add(coursesBtn);
        add(adviceBtn);
        add(exitBtn);
    }

    //creates button with color hover effect (only color changed)
    private JButton makeButton(String text, String cmd) 
    {
        JButton b = new JButton(text);
        b.setActionCommand(cmd);
        b.addActionListener(this);
        b.setPreferredSize(new Dimension(180, 50));
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setFocusPainted(false);

        //refined colors: calm blue tone with bright hover
        Color base = new Color(200, 220, 255);   // base soft blue
        Color hover = new Color(120, 170, 255);  // bright hover blue
        Color press = new Color(90, 140, 230);   // pressed blue tone

        b.setBackground(base);
        b.setForeground(new Color(25, 40, 70));
        b.setOpaque(true);
        b.setBorderPainted(false);

        //hover and press color transitions
        b.addMouseListener(new MouseAdapter() 
        {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e) { b.setBackground(base); }
            @Override public void mousePressed(MouseEvent e) { b.setBackground(press); }
            @Override public void mouseReleased(MouseEvent e) { b.setBackground(hover); }
        });

        return b;
    }

    //switch cards or exit app (unchanged)
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String cmd = e.getActionCommand();

        if (!cmd.equals("EXIT")) 
        {
            frame.showCard(cmd);
            return;
        }

        //confirm before exiting
        int result = JOptionPane.showConfirmDialog(
            frame,
            "Are you sure you want to exit?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) 
        {
            System.exit(0);
        }
    }
}
