/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import advisor.model.Course;
import advisor.repo.CourseRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

//panel shows all courses in table
//+refresh to reload latest data from the repo
public class CoursesPanel extends JPanel
{
    private final CourseRepository repo;
    private JTable table;
    private DefaultTableModel model;

    public CoursesPanel(CourseRepository repo)
    {
        this.repo = repo;

        //panel setup and padding
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //title lctn
        JLabel title = new JLabel("Course Catalogue", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        //table setup with headers
        model = new DefaultTableModel(new Object[]{"CODE", "NAME", "POINTS", "PREREQUISITES"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(36);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));

        //make table cells non-editable by user
        table.setDefaultEditor(Object.class, null);

        //tooltip only shows if text is truncated
        table.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row > -1 && col > -1) {
                    Object value = table.getValueAt(row, col);
                    if (value != null) {
                        String text = value.toString();
                        java.awt.FontMetrics fm = table.getFontMetrics(table.getFont());
                        int textWidth = fm.stringWidth(text);
                        int colWidth = table.getColumnModel().getColumn(col).getWidth();
                        if (textWidth > colWidth - 10) table.setToolTipText(text);
                        else table.setToolTipText(null);
                    }
                } else table.setToolTipText(null);
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        //refresh button
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setPreferredSize(new Dimension(200, 50));
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        buttons.add(refreshBtn);
        add(buttons, BorderLayout.SOUTH);

        //refresh when button clicked
        refreshBtn.addActionListener(e -> refreshTable());
        refreshTable(); //refresh insta
    }

    private void refreshTable()
    {
        try
        {
            //load courses from repo
            List<Course> courses = repo.findAll();
            model.setRowCount(0); //clear old rows
            for (Course c : courses) {
                String prereqs = c.getPrerequisites().isEmpty()
                        ? "-"
                        : String.join(", ", c.getPrerequisites());
                //add course info to table
                model.addRow(new Object[]{
                    c.getCode(),
                    c.getName(),
                    c.getPoints(),
                    prereqs
                });
            }
        } catch (Exception e)
        {
            //error handling
            JOptionPane.showMessageDialog(this, "Failed to load courses: " + e.getMessage());
        }
    }
}
