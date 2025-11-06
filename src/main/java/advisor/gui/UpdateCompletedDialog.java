/*
 * UpdateCompletedDialog.java
 * Simple dialog for ticking completed courses instead of typing them manually.
 */
package advisor.gui;

import advisor.model.Student;
import advisor.model.Course;
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
    private final JPanel coursePanel;
    private final java.util.List<JCheckBox> checkBoxes = new java.util.ArrayList<>();

    public UpdateCompletedDialog(JFrame parent, Student student, StudentRepository repo, CourseRepository courseRepo) {
        super(parent, "Update Completed Courses", true);
        this.student = student;
        this.studentRepo = repo;
        this.courseRepo = courseRepo;

        setLayout(new BorderLayout(10, 10));
        setSize(500, 500);
        setLocationRelativeTo(parent);

        // Title
        JLabel title = new JLabel("Select completed courses for " + student.getName(), SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        // Scrollable panel of checkboxes
        coursePanel = new JPanel();
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(coursePanel);
        add(scroll, BorderLayout.CENTER);

        // Buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        bottom.add(save);
        bottom.add(cancel);
        add(bottom, BorderLayout.SOUTH);

        // Load courses and mark completed ones
        loadCourses();

        // Button actions
        save.addActionListener(e -> onSave());
        cancel.addActionListener(e -> dispose());
    }

    // Load all courses from DB as checkboxes
    private void loadCourses() {
        try {
            List<Course> allCourses = courseRepo.findAll();
            List<String> completed = student.getCompleted();

            for (Course c : allCourses) {
                JCheckBox box = new JCheckBox(c.getCode() + " - " + c.getName());
                box.setSelected(completed.contains(c.getCode()));
                box.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                checkBoxes.add(box);
                coursePanel.add(box);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading courses: " + e.getMessage());
        }
    }

    // Save ticked courses
    private void onSave() {
        List<String> selectedCodes = checkBoxes.stream()
                .filter(JCheckBox::isSelected)
                .map(b -> b.getText().split(" - ")[0].trim())
                .collect(Collectors.toList());

        student.clearCompleted();
        selectedCodes.forEach(student::addCompleted);

        try {
            studentRepo.upsert(student);
            JOptionPane.showMessageDialog(this, "Updated completed courses for " + student.getName());
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving: " + e.getMessage());
        }
    }
}
