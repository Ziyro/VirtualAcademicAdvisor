/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import advisor.model.Student;
import advisor.repo.StudentRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentsPanel extends JPanel {
    private final StudentRepository repo;
    private final AppController controller;
    private JTable table;
    private DefaultTableModel model;

    public StudentsPanel(AppController controller, StudentRepository repo) {
        this.repo = repo;
        this.controller = controller;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Student Records", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"ID", "Name", "GPA", "Goal"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(36);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
        JButton addBtn = new JButton("Add Student");
        JButton refreshBtn = new JButton("Refresh");
        for (JButton b : new JButton[]{addBtn, refreshBtn}) {
            b.setFont(new Font("Segoe UI", Font.BOLD, 16));
            b.setPreferredSize(new Dimension(200, 50));
            buttons.add(b);
        }
        add(buttons, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            StudentFormDialog dialog = new StudentFormDialog(controller.getFrame(), repo);
            dialog.setVisible(true);
            refreshTable();
        });
        refreshBtn.addActionListener(e -> refreshTable());

        refreshTable();
    }

    private void refreshTable() {
        try {
            List<Student> students = repo.findAll();
            model.setRowCount(0);
            for (Student s : students) {
                model.addRow(new Object[]{s.getId(), s.getName(), s.getGpa(), s.getGoal()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load students: " + e.getMessage());
        }
    }
}
