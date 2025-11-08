package advisor.gui;

import advisor.model.Student;
import advisor.repo.StudentRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

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

        //make table cells non-editable by user
        table.setDefaultEditor(Object.class, null);

        add(new JScrollPane(table), BorderLayout.CENTER);

        //tooltip only shows if text is truncated
        table.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row > -1 && col > -1) {
                    Object value = table.getValueAt(row, col);
                    if (value != null) {
                        String text = value.toString();
                        java.awt.FontMetrics fm = table.getFontMetrics(table.getFont());
                        int textWidth = fm.stringWidth(text);
                        int colWidth = table.getColumnModel().getColumn(col).getWidth();
                        if (textWidth > colWidth - 10) table.setToolTipText(text);
                        else table.setToolTipText(null);
                    }
                } else table.setToolTipText(null);
            }
        });

        //buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
        JButton addBtn = new JButton("Add Student");
        JButton refreshBtn = new JButton("Refresh");
        JButton updateCoursesBtn = new JButton("Update Courses");
        JButton showCompletedBtn = new JButton("Show Completed");

        //apply hover styling
        for (JButton b : new JButton[]{addBtn, refreshBtn, updateCoursesBtn, showCompletedBtn})
        {
            styleActionButton(b);
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

    //hover effect for buttons 
    private void styleActionButton(JButton b)
    {
        b.setFocusPainted(false);
        b.setBackground(new Color(245, 247, 250)); //base tone
        b.setForeground(new Color(40, 60, 90));
        b.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));

        b.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e)
            {
                b.setBackground(new Color(70, 130, 180)); //soft blue hover
                b.setForeground(Color.WHITE);
                b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e)
            {
                b.setBackground(new Color(245, 247, 250)); //reset
                b.setForeground(new Color(40, 60, 90));
                b.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
}
