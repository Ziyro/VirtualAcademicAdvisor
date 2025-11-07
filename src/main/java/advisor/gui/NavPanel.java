/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//bottom navnbar with hover colour effect 
public class NavPanel extends JPanel implements ActionListener 
{
    private final AdvisorFrame frame;

    public NavPanel(AdvisorFrame frame) 
    {
        this.frame = frame;
        setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        setBackground(new Color(245, 247, 250)); 

        
        add(makeButton("Dashboard", "DASH"));
        add(makeButton("Students", "STUDENTS"));
        add(makeButton("Courses", "COURSES"));
        add(makeButton("Get Advice", "ADVICE"));
        add(makeButton("Exit", "EXIT"));
    }

    //creates button with hover color change effect
    private JButton makeButton(String text, String cmd) 
    {
        JButton b = new JButton(text);
        b.setActionCommand(cmd);
        b.addActionListener(this);

        b.setPreferredSize(new Dimension(180, 50));
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setFocusPainted(false);
        b.setBackground(new Color(235, 237, 240)); //default light gray
        b.setForeground(new Color(40, 40, 40));
        b.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        //hover color change
        b.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                b.setBackground(new Color(66, 135, 245)); //blue hover
                b.setForeground(Color.WHITE);
                b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                b.setBackground(new Color(235, 237, 240)); //reset
                b.setForeground(new Color(40, 40, 40));
                b.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if ("EXIT".equals(e.getActionCommand())) 
        {
            System.exit(0);
        } 
        else 
        {
            frame.showCard(e.getActionCommand());
        }
    }
}
