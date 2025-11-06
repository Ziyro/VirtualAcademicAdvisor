/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.gui;

import advisor.model.Student;
import advisor.model.Course;
import advisor.repo.StudentRepository;
import advisor.repo.CourseRepository;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//dialog for updating student completed courses (checkbox version with persistence)
public class UpdateCompletedDialog extends JDialog
{
    private final Student student;
    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final List<JCheckBox> boxes = new ArrayList<>();

    public UpdateCompletedDialog(JFrame parent, Student student, StudentRepository repo, CourseRepository courseRepo)
    {
        super(parent, "Update Courses", true);
        this.student = student;
        this.studentRepo = repo;
        this.courseRepo = courseRepo;

        //basic layout and size
        setLayout(new BorderLayout(10, 10));
        setSize(550, 500);
        setLocationRelativeTo(parent);

        //label at top
        add(new JLabel("Select completed courses:"), BorderLayout.NORTH);

        //scrollable panel for checkboxes
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        try
        {
            //get all courses from repo
            List<Course> allCourses = courseRepo.findAll();
            List<String> done = student.getCompleted();

            //create checkbox for each course
            for (Course c : allCourses)
            {
                JCheckBox box = new JCheckBox(c.getCode() + " - " + c.getName());
                box.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                if (done.contains(c.getCode())) box.setSelected(true); //preselect completed
                boxes.add(box);
                listPanel.add(box);
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, "Error loading courses: " + e.getMessage());
        }

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scroll, BorderLayout.CENTER);

        //save and cancel buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        bottom.add(save); bottom.add(cancel);
        add(bottom, BorderLayout.SOUTH);

        //actions
        save.addActionListener(e -> onSave());
        cancel.addActionListener(e -> dispose());
    }

    private void onSave()
    {
        try
        {
            //get all selected courses
            List<String> selected = new ArrayList<>();
            for (JCheckBox box : boxes)
            {
                if (box.isSelected())
                {
                    String code = box.getText().split(" - ")[0].trim();
                    selected.add(code);
                }
            }

            //keep previously completed + add new ones
            List<String> updated = new ArrayList<>(student.getCompleted());

            //add new ones that weren’t already there
            for (String c : selected)
            {
                if (!updated.contains(c)) updated.add(c);
            }

            //remove ones that got deselected
            updated.removeIf(c -> !selected.contains(c));

            //apply final list to student
            student.getCompleted().clear();
            updated.forEach(student::addCompleted);

            //save changes
            studentRepo.upsert(student);
            JOptionPane.showMessageDialog(this, "Updated " + student.getName());
            dispose();
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
