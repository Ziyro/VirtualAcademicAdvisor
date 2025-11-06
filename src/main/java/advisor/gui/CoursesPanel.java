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

public class CoursesPanel extends JPanel {
    private final CourseRepository repo;
    private JTable table;
    private DefaultTableModel model;

    public CoursesPanel(CourseRepository repo) {
        this.repo = repo;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Course Catalogue", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Match display headers to uppercase DB schema for clarity
        model = new DefaultTableModel(new Object[]{"CODE", "NAME", "POINTS", "PREREQUISITES"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(36);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setPreferredSize(new Dimension(200, 50));
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        buttons.add(refreshBtn);
        add(buttons, BorderLayout.SOUTH);

        // Load data initially and on refresh
        refreshBtn.addActionListener(e -> refreshTable());
        refreshTable();
    }

    private void refreshTable() {
        try {
            List<Course> courses = repo.findAll();
            model.setRowCount(0);
            for (Course c : courses) {
                String prereqs = c.getPrerequisites().isEmpty()
                        ? "-"
                        : String.join(", ", c.getPrerequisites());
                model.addRow(new Object[]{
                    c.getCode(),
                    c.getName(),
                    c.getPoints(),
                    prereqs
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load courses: " + e.getMessage());
        }
    }
}
