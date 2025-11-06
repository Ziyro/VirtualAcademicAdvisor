/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package advisor.gui;

import advisor.model.Recommendation;
import advisor.model.Student;
import advisor.repo.StudentRepository;
import advisor.service.AdvisorService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AdvicePanel extends JPanel {

    private final AdvisorService advisor;
    private final StudentRepository studentRepo;
    private final JTextArea area;

    // Panel that shows the recommendations for a selected student
    public AdvicePanel(AdvisorService advisor, StudentRepository studentRepo) {
        this.advisor = advisor;
        this.studentRepo = studentRepo;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Study Advice", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        add(new JScrollPane(area), BorderLayout.CENTER);

        JButton refresh = new JButton("Generate Advice");
        refresh.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(refresh, BorderLayout.SOUTH);

        refresh.addActionListener(e -> showAdvice());
    }

    // Generates and displays course recommendations
    private void showAdvice() {
        String id = JOptionPane.showInputDialog(this, "Enter Student ID:");
        if (id == null || id.isBlank()) return;

        Student student = studentRepo.findById(id).orElse(null);
        if (student == null) {
            JOptionPane.showMessageDialog(this, "No student found with ID " + id);
            return;
        }

        try {
            List<Recommendation> recs = advisor.recommendNextSemester(student);
            StringBuilder sb = new StringBuilder("Recommendations for " + student.getName() + ":\n\n");
            for (Recommendation r : recs) sb.append(r.toString()).append("\n");
            area.setText(sb.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
