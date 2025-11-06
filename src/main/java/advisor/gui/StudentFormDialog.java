/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import advisor.model.Student;
import advisor.repo.StudentRepository;
import javax.swing.*;
import java.awt.*;

//dialog window for adding or editing a student
//handles input validtion and saving to repo
public class StudentFormDialog extends JDialog {
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField gpaField = new JTextField();
    private final JTextField goalField = new JTextField();
    private final StudentRepository repo;

    public StudentFormDialog(JFrame parent, StudentRepository repo) {
        super(parent, "Add Student", true);
        this.repo = repo;

        //setup dialog layout
        setLayout(new GridLayout(5, 2, 10, 10));
        setSize(460, 300);
        setLocationRelativeTo(parent);

        //form fields
        add(new JLabel("Student ID:")); add(idField);
        add(new JLabel("Full name:")); add(nameField);
        add(new JLabel("GPA (0.0 - 9.0):")); add(gpaField);
        add(new JLabel("Goal (optional):")); add(goalField);

        //buttons
        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        add(save); add(cancel);

        //button actions
        save.addActionListener(e -> onSave());
        cancel.addActionListener(e -> dispose());
    }

    private void onSave() {
        //validate inputs before saving
        if (!GuiValidator.requireDigits(idField, "Student ID")) return;
        if (!GuiValidator.requirePersonName(nameField, "name")) return;
        if (!GuiValidator.requireGpaInRange(gpaField, "GPA", 0.0, 9.0)) return;

        //grab data from fields
        String id = idField.getText().trim();
        String name = nameField.getText().trim().replaceAll("\\s+", " ");
        double gpa = Double.parseDouble(gpaField.getText().trim());
        String goal = goalField.getText().trim();

        try {
            //save to repo
            repo.upsert(new Student(id, name, gpa, goal));
            JOptionPane.showMessageDialog(this, "Student saved.");
            dispose();
        } catch (Exception ex) {
            //show error if something failed
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
