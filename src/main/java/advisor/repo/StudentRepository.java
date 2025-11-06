/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.repo;

import advisor.model.DegreePlan;
import advisor.model.Student;
import java.sql.*;
import java.util.*;

//handles all student database actions
//insert/update/find/list student records
public class StudentRepository 
{

    private final Connection conn;

    public StudentRepository(Connection conn) 
    {
        this.conn = conn;
    }

    //adds or updates a student record
    public void upsert(Student s) 
    {
        String sqlCheck = "SELECT COUNT(*) FROM Students WHERE id=?";
        String sqlInsert = "INSERT INTO Students (id, name, gpa, goal) VALUES (?, ?, ?, ?)";
        String sqlUpdate = "UPDATE Students SET name=?, gpa=?, goal=? WHERE id=?";
        try (PreparedStatement check = conn.prepareStatement(sqlCheck)) 
        {
            check.setString(1, s.getId());
            try (ResultSet rs = check.executeQuery()) 
            {
                rs.next();
                boolean exists = rs.getInt(1) > 0;
                if (exists) 
                {
                    //update existing student
                    try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) 
                    {
                        ps.setString(1, s.getName());
                        ps.setDouble(2, s.getGpa());
                        ps.setString(3, s.getGoal());
                        ps.setString(4, s.getId());
                        ps.executeUpdate();
                    }
                } else 
                {
                    //insert new student
                    try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) 
                    {
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

    //returns a list of all students
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) 
        {
            while (rs.next()) 
            {
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

    //finds one student by their ID
    public Optional<Student> findById(String id) 
    {
        String sql = "SELECT * FROM Students WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) 
            {
                if (rs.next()) 
                {
                    return Optional.of(new Student(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getDouble("gpa"),
                            rs.getString("goal")));
                }
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    //placeholder for saving degree plans later
    public void savePlan(String id, DegreePlan plan) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
