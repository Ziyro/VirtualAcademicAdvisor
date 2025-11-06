/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import advisor.model.Recommendation;
import advisor.model.Student;
import advisor.repo.CourseRepository;
import advisor.repo.StudentRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdvicePanel extends JPanel {
    private final AppController controller;
    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private JTable table;
    private DefaultTableModel model;

    public AdvicePanel(AppController controller, StudentRepository studentRepo, CourseRepository courseRepo) {
        this.controller = controller;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Generate Study Advice", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Course", "Message"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(36);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
        JButton adviseBtn = new JButton("Get Advice");
        adviseBtn.setPreferredSize(new Dimension(200, 50));
        adviseBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bottom.add(adviseBtn);
        add(bottom, BorderLayout.SOUTH);

        adviseBtn.addActionListener(e -> onAdvise());
    }

    private void onAdvise() {
        String id = JOptionPane.showInputDialog(this, "Enter Student ID (digits only):");
        if (id == null) return;
        id = id.trim();
        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "ID must be digits only.");
            return;
        }
        try {
            var maybe = studentRepo.findById(id);
            if (maybe.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Student not found.");
                return;
            }
            Student s = maybe.get();
            List<Recommendation> recs = controller.getAdvisorService().recommendNextSemester(s);

            model.setRowCount(0);
            for (Recommendation r : recs) {
                model.addRow(new Object[]{r.getCode(), r.getMessage()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
