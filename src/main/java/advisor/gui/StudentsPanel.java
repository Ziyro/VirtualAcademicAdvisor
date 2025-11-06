//Displays all students and allows adding new ones or updating their completed courses.

package advisor.gui;

import advisor.model.Student;
import advisor.repo.CourseRepository;
import advisor.repo.StudentRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentsPanel extends JPanel {

    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private JTable table;
    private DefaultTableModel model;

    public StudentsPanel(AppController controller, StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
        this.courseRepo = controller.getCourseRepo();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Student Records", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        // set up table with nice look
        model = new DefaultTableModel(new Object[]{"ID", "Name", "GPA", "Goal"}, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));

        refreshTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // bottom buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton addBtn = new JButton("Add Student");
        JButton updateBtn = new JButton("Update Completed");
        JButton refreshBtn = new JButton("Refresh");

        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        updateBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));

        buttons.add(refreshBtn);
        buttons.add(addBtn);
        buttons.add(updateBtn);
        add(buttons, BorderLayout.SOUTH);

        // refresh button reloads from DB
        refreshBtn.addActionListener(e -> refreshTable());

        // add student popup
        addBtn.addActionListener(e -> {
            StudentFormDialog dialog = new StudentFormDialog((JFrame) SwingUtilities.getWindowAncestor(this), studentRepo);
            dialog.setVisible(true);
            refreshTable();
        });

        // update completed courses
        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a student first.");
                return;
            }

            // get ID from table
            String id = (String) model.getValueAt(row, 0);
            Student s = studentRepo.findById(id).orElse(null);
            if (s == null) {
                JOptionPane.showMessageDialog(this, "Could not find student record.");
                return;
            }

            // open dialog to edit completed courses
            UpdateCompletedDialog dialog = new UpdateCompletedDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    s,
                    studentRepo,
                    courseRepo
            );
            dialog.setVisible(true);
            refreshTable();
        });
    }

    /** Loads all students from the DB into the table. */
    private void refreshTable() {
        model.setRowCount(0);
        List<Student> students = studentRepo.findAll();
        for (Student s : students) {
            model.addRow(new Object[]{
                    s.getId(),
                    s.getName(),
                    s.getGpa(),
                    s.getGoal()
            });
        }
    }
}
