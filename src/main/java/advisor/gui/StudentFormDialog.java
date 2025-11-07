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
//handles input validation and saving to repo
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
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 12));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25)); // added spacing inside dialog

        //form fields
        formPanel.add(new JLabel("Student ID:")); formPanel.add(idField);
        formPanel.add(new JLabel("Full name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("GPA (0.0 - 9.0):")); formPanel.add(gpaField);
        formPanel.add(new JLabel("Goal (optional):")); formPanel.add(goalField);

        //buttons
        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        formPanel.add(save);
        formPanel.add(cancel);

        add(formPanel);

        //dialog sizing and positioning
        setSize(480, 320);
        setLocationRelativeTo(parent);

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
