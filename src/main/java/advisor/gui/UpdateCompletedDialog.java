/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import advisor.model.Student;
import advisor.repo.StudentRepository;
import advisor.repo.CourseRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateCompletedDialog extends JDialog {
    private final Student student;
    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final JTextArea area;

    public UpdateCompletedDialog(JFrame parent, Student student, StudentRepository repo, CourseRepository courseRepo) {
        super(parent, "Update Courses", true);
        this.student = student;
        this.studentRepo = repo;
        this.courseRepo = courseRepo;

        setLayout(new BorderLayout(10, 10));
        setSize(550, 400);
        setLocationRelativeTo(parent);

        add(new JLabel("Enter completed course codes (comma separated):"), BorderLayout.NORTH);
        area = new JTextArea(String.join(", ", student.getCompleted()));
        area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        bottom.add(save); bottom.add(cancel);
        add(bottom, BorderLayout.SOUTH);

        save.addActionListener(e -> onSave());
        cancel.addActionListener(e -> dispose());
    }

    private void onSave() {
        String text = area.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter at least one course code.");
            return;
        }

        List<String> codes = List.of(text.split(","))
                .stream().map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());

        List<String> invalid = codes.stream()
                .filter(c -> courseRepo.findByCode(c).isEmpty())
                .collect(Collectors.toList());

        if (!invalid.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Invalid course codes:\n" + String.join(", ", invalid),
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        student.clearCompleted();
        codes.forEach(student::addCompleted);

        try {
            studentRepo.upsert(student);
            JOptionPane.showMessageDialog(this, "Updated " + student.getName());
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
