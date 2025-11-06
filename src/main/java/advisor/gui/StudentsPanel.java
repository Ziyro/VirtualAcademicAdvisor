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
import java.util.*; //kept as is, but we fully-qualify types below

//panel shows all student records
//add, refresh, update courses, or show completed ones
public class StudentsPanel extends JPanel
{
    private final StudentRepository repo;
    private final AppController controller;
    private JTable table;
    private DefaultTableModel model;
    private final java.util.Map<String, java.util.List<String>> completedCache = new java.util.HashMap<>();

    public StudentsPanel(AppController controller, StudentRepository repo)
    {
        this.repo = repo;
        this.controller = controller;

        //setup main layout
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //title
        JLabel title = new JLabel("Student Records", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        //setup table students
        model = new DefaultTableModel(new Object[]{"ID", "Name", "GPA", "Goal"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(36);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        add(new JScrollPane(table), BorderLayout.CENTER);

        //buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
        JButton addBtn = new JButton("Add Student");
        JButton refreshBtn = new JButton("Refresh");
        JButton updateCoursesBtn = new JButton("Update Courses");
        JButton showCompletedBtn = new JButton("Show Completed");

        for (JButton b : new JButton[]{addBtn, refreshBtn, updateCoursesBtn, showCompletedBtn})
        {
            b.setFont(new Font("Segoe UI", Font.BOLD, 16));
            b.setPreferredSize(new Dimension(200, 50));
            buttons.add(b);
        }
        add(buttons, BorderLayout.SOUTH);

        //add student
        addBtn.addActionListener(e ->
        {
            StudentFormDialog dialog = new StudentFormDialog(controller.getFrame(), repo);
            dialog.setVisible(true);
            refreshTable();
        });

        //refresh list
        refreshBtn.addActionListener(e -> refreshTable());

        //update completed courses
        updateCoursesBtn.addActionListener(e ->
        {
            int row = table.getSelectedRow();
            if (row == -1)
            {
                JOptionPane.showMessageDialog(this, "Select a student first.");
                return;
            }

            String id = (String) model.getValueAt(row, 0);
            repo.findById(id).ifPresentOrElse(s ->
            {
                UpdateCompletedDialog dialog = new UpdateCompletedDialog(controller.getFrame(), s, repo, controller.getCourseRepo());
                dialog.setVisible(true);
                completedCache.put(s.getId(), s.getCompleted()); //update cache
            }, () -> JOptionPane.showMessageDialog(this, "Student not found."));
        });

        //show completed courses
        showCompletedBtn.addActionListener(e ->
        {
            int row = table.getSelectedRow();
            if (row == -1)
            {
                JOptionPane.showMessageDialog(this, "Select a student first.");
                return;
            }

            String id = (String) model.getValueAt(row, 0);
            repo.findById(id).ifPresentOrElse(s ->
            {
                java.util.List<String> completed = completedCache.getOrDefault(id, s.getCompleted());
                String msg = completed.isEmpty()
                        ? "No completed courses recorded."
                        : String.join(", ", completed);
                JOptionPane.showMessageDialog(this, msg, s.getName() + " - Completed Courses", JOptionPane.INFORMATION_MESSAGE);
            }, () -> JOptionPane.showMessageDialog(this, "Student not found."));
        });

        refreshTable();
    }

    private void refreshTable()
    {
        try
        {
            //get all students and fill table
            java.util.List<Student> students = repo.findAll();
            model.setRowCount(0);
            for (Student s : students)
            {
                model.addRow(new Object[]{s.getId(), s.getName(), s.getGpa(), s.getGoal()});
            }
        }
        catch (Exception e)
        {
            //show message if something fails
            JOptionPane.showMessageDialog(this, "Failed to load students: " + e.getMessage());
        }
    }
}
