package advisor.repo;

import advisor.model.Course;
import java.sql.*;
import java.util.*;

public class CourseRepository {

           private final Connection conn;

  
  public CourseRepository(Connection conn) {
        this.conn = conn;
    }

    // gets all courses from the database 
    public List<Course> findAll() throws SQLException {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT CODE, NAME, POINTS, PREREQS FROM COURSES ORDER BY CODE";
        try (Statement s = conn.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                String code = rs.getString("CODE");
                String name = rs.getString("NAME");
                int pts = rs.getInt("POINTS");
                String pre = rs.getString("PREREQS");

                // if course has prereqs, split them into a list
             List<String> prereq = (pre == null || pre.isBlank())
                        ? List.of()
                        : Arrays.asList(pre.split(";"));

                
                list.add(new Course(code, name, pts, prereq));
            }
        }
        return list;
    }

    //tp find a course by its code
    // mainly used when checking completed courses or validating input
    public Optional<Course> findByCode(String code) {
        String sql = "SELECT CODE, NAME, POINTS, PREREQS FROM COURSES WHERE CODE=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("NAME");
                    int pts = rs.getInt("POINTS");
                    String pre = rs.getString("PREREQS");
                    List<String> prereq = (pre == null || pre.isBlank())
                            ? List.of()
                            : Arrays.asList(pre.split(";"));
                    return Optional.of(new Course(code, name, pts, prereq));
                }
            }
        } catch (SQLException e) {
            System.err.println("findByCode error: " + e.getMessage());
        }
        return Optional.empty();
    }

    // used when adding new courses manually
    public void insert(Course c) {
        String sql = "INSERT INTO COURSES (CODE, NAME, POINTS, PREREQS) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getCode());
            ps.setString(2, c.getName());
            ps.setInt(3, c.getPoints());
            ps.setString(4, String.join(";", c.getPrerequisites()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Insert course error: " + e.getMessage());
        }
    }
}
