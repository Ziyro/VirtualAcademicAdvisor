package advisor.gui;

import advisor.model.Student;
import advisor.model.Recommendation;
import advisor.repo.*;
import advisor.service.*;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class AdvicePanel extends JPanel {
    private final JTextArea area;

    public AdvicePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Generate Study Advice", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        add(new JScrollPane(area), BorderLayout.CENTER);

        JButton adviseBtn = new JButton("Get Advice");
        adviseBtn.setPreferredSize(new Dimension(200, 50));
        adviseBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        adviseBtn.addActionListener(e -> showAdvice());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
        bottom.add(adviseBtn);
        add(bottom, BorderLayout.SOUTH);
    }

    private void showAdvice() 
    {
        try 
        {
           Connection conn = Database.getConnection();
            CourseRepository courseRepo = new CourseRepository(conn);
           StudentRepository studentRepo = new StudentRepository(conn);
            AdvisorService advisor = new RuleBasedAdvisor(courseRepo);

            // In real GUI, user would pick student ID from list or form
            Student s = new Student("S12345", "John Doe", 3.2, "Become Software Engineer");

            List<Recommendation> recs = advisor.recommendNextSemester(s);

            area.setText("");
            for (Recommendation r : recs) {
                area.append(r.toString() + "\n");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error generating advice:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
