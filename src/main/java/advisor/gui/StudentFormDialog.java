/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

  // Form window for adding or editingstudent record. Opens as a small popup when user tries ading stdnts
 

package advisor.gui;

import advisor.model.Student;
import advisor.repo.StudentRepository;
import javax.swing.*;
import java.awt.*;

public class StudentFormDialog extends JDialog {
     private final JTextField idField = new JTextField();
     private final JTextField nameField = new JTextField();
    private final JTextField gpaField = new JTextField();
     private final JTextField goalField = new JTextField();
    private final StudentRepository repo;

    public StudentFormDialog(JFrame parent, StudentRepository repo) {
        super(parent, "Add Student", true);
        this.repo = repo;

        setLayout(new GridLayout(5, 2, 10, 10));
        setSize(460, 300);
        setLocationRelativeTo(parent);

        add(new JLabel("Student ID:")); add(idField);
        add(new JLabel("Full name:")); add(nameField);
        add(new JLabel("GPA (0.0 - 9.0):")); add(gpaField);
        add(new JLabel("Goal (optional):")); add(goalField);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        add(save); add(cancel);

        // button actions
        save.addActionListener(e -> onSave());
        cancel.addActionListener(e -> dispose());
    }

    private void onSave() {
        // basic validation
        if (!GuiValidator.requireDigits(idField, "Student ID")) return;
        if (!GuiValidator.requirePersonName(nameField, "name")) return;
        if (!GuiValidator.requireGpaInRange(gpaField, "GPA", 0.0, 9.0)) return;

        String id = idField.getText().trim();
        String name = nameField.getText().trim().replaceAll("\\s+", " ");
        double gpa = Double.parseDouble(gpaField.getText().trim());
        String goal = goalField.getText().trim();

        try {
            repo.upsert(new Student(id, name, gpa, goal));
            JOptionPane.showMessageDialog(this, "Student saved successfully.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
