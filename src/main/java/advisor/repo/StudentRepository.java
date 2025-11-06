/*
 * Handles saving and loading student data (original working version).
 * Uses String IDs — this matches your GUI and Derby database.
 */
package advisor.repo;

import advisor.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StudentRepository {

    private final Connection conn;

    public StudentRepository(Connection conn) throws SQLException {
        this.conn = conn;
        try {
            this.conn.setAutoCommit(true);
        } catch (SQLException ignore) {
        }
    }

    public void upsert(Student s) {
        final String checkSql   = "SELECT COUNT(*) FROM Students WHERE id = ?";
        final String insertSql  = "INSERT INTO Students (id, name, gpa, goal, completed) VALUES (?, ?, ?, ?, ?)";
        final String updateSql  = "UPDATE Students SET name = ?, gpa = ?, goal = ?, completed = ? WHERE id = ?";

        String completedStr = String.join(";", s.getCompleted());

        try (PreparedStatement check = conn.prepareStatement(checkSql)) {
            check.setString(1, s.getId());
            try (ResultSet rs = check.executeQuery()) {
                rs.next();
                boolean exists = rs.getInt(1) > 0;

                if (exists) {
                    try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                        ps.setString(1, s.getName());
                        ps.setDouble(2, s.getGpa());
                        ps.setString(3, s.getGoal());
                        ps.setString(4, completedStr);
                        ps.setString(5, s.getId());
                        ps.executeUpdate();
                    }
                } else {
                    try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                        ps.setString(1, s.getId());
                        ps.setString(2, s.getName());
                        ps.setDouble(3, s.getGpa());
                        ps.setString(4, s.getGoal());
                        ps.setString(5, completedStr);
                        ps.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        final String sql = "SELECT id, name, gpa, goal, completed FROM Students";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Student s = new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("gpa"),
                        rs.getString("goal")
                );

                String completedStr = rs.getString("completed");
                if (completedStr != null && !completedStr.isBlank()) {
                    Arrays.stream(completedStr.split(";"))
                            .map(String::trim)
                            .filter(t -> !t.isEmpty())
                            .forEach(s::addCompleted);
                }

                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Optional<Student> findById(String id) {
        final String sql = "SELECT id, name, gpa, goal, completed FROM Students WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student s = new Student(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getDouble("gpa"),
                            rs.getString("goal")
                    );

                    String completedStr = rs.getString("completed");
                    if (completedStr != null && !completedStr.isBlank()) {
                        Arrays.stream(completedStr.split(";"))
                                .map(String::trim)
                                .filter(t -> !t.isEmpty())
                                .forEach(s::addCompleted);
                    }

                    return Optional.of(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
