package advisor.repo;

import advisor.model.DegreePlan;
import advisor.model.Student;
import java.sql.*;
import java.util.*;

public class StudentRepository {

    private final Connection conn;

    public StudentRepository(Connection conn) {
        this.conn = conn;
    }

    // insert or update student info
    public void upsert(Student s) {
         String sqlCheck = "SELECT COUNT(*) FROM Students WHERE id=?";
       String sqlInsert = "INSERT INTO Students (id, name, gpa, goal) VALUES (?, ?, ?, ?)";
        String sqlUpdate = "UPDATE Students SET name=?, gpa=?, goal=? WHERE id=?";
       try (PreparedStatement check = conn.prepareStatement(sqlCheck)) {
            check.setString(1, s.getId());
            try (ResultSet rs = check.executeQuery()) {
                rs.next();
                boolean exists = rs.getInt(1) > 0;
                if (exists) {
                    try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                        ps.setString(1, s.getName());
                        ps.setDouble(2, s.getGpa());
                        ps.setString(3, s.getGoal());
                        ps.setString(4, s.getId());
                        ps.executeUpdate();
                    }
                } else {
                    try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                        ps.setString(1, s.getId());
                        ps.setString(2, s.getName());
                        ps.setDouble(3, s.getGpa());
                        ps.setString(4, s.getGoal());
                        ps.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Save student error: " + e.getMessage());
        }
    }

    // returns all students in DB
    public List<Student> findAll() {
          List<Student> list = new ArrayList<>();
      String sql = "SELECT * FROM Students";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("gpa"),
                        rs.getString("goal")));
            }
        } catch (SQLException e) {
             e.printStackTrace();
        }
        return list;
    }

    // helper to find by id (used in both GUI + CUI)
    public Optional<Student> findById(String id) {
        String sql = "SELECT * FROM Students WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Student(
                           rs.getString("id"),
                           rs.getString("name"),
                            rs.getDouble("gpa"),
                            rs.getString("goal")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // placeholder for saving a degree plan 
    public void savePlan(String id, DegreePlan plan) {
        
        System.out.println("savePlan() not implemented yet, skipping for now.");
    }
}
