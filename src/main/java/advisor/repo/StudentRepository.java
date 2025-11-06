/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.repo;

import advisor.model.DegreePlan;
import advisor.model.Student;
import java.sql.*;
import java.util.*;

//manages all student-related DB actions
//insert/update/find/list student records
public class StudentRepository 
{
    private final Connection conn;

    public StudentRepository(Connection conn) 
    {
        this.conn = conn;
    }

    //adds or updates a student record including completed courses
    public void upsert(Student s) 
    {
        String sqlCheck = "SELECT COUNT(*) FROM Students WHERE id=?";
        String sqlInsert = "INSERT INTO Students (id, name, gpa, goal, completed) VALUES (?, ?, ?, ?, ?)";
        String sqlUpdate = "UPDATE Students SET name=?, gpa=?, goal=?, completed=? WHERE id=?";

        try 
        {
            conn.setAutoCommit(true);
            boolean exists = false;

            //check if student exists
            try (PreparedStatement check = conn.prepareStatement(sqlCheck)) 
            {
                check.setString(1, s.getId());
                try (ResultSet rs = check.executeQuery()) 
                {
                    if (rs.next() && rs.getInt(1) > 0) exists = true;
                }
            }

            //convert completed list to string
            String completedString = String.join(";", s.getCompleted());

            if (exists) 
            {
                //update existing record
                try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) 
                {
                    ps.setString(1, s.getName());
                    ps.setDouble(2, s.getGpa());
                    ps.setString(3, s.getGoal());
                    ps.setString(4, completedString);
                    ps.setString(5, s.getId());
                    ps.executeUpdate();
                }
            } 
            else 
            {
                //insert new record
                try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) 
                {
                    ps.setString(1, s.getId());
                    ps.setString(2, s.getName());
                    ps.setDouble(3, s.getGpa());
                    ps.setString(4, s.getGoal());
                    ps.setString(5, completedString);
                    ps.executeUpdate();
                }
            }
            conn.commit();
        } 
        catch (SQLException e) 
        {
            System.err.println("Save student error: " + e.getMessage());
        }
    }

    //returns all students (including completed)
    public List<Student> findAll() 
    {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) 
        {
            while (rs.next()) 
            {
                Student s = new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("gpa"),
                        rs.getString("goal"));

                String completedStr = rs.getString("completed");
                if (completedStr != null && !completedStr.isBlank()) 
                {
                    for (String c : completedStr.split(";")) 
                    {
                        s.addCompleted(c.trim());
                    }
                }
                list.add(s);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return list;
    }

    //finds a student by ID
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
                    Student s = new Student(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getDouble("gpa"),
                            rs.getString("goal"));

                    String completedStr = rs.getString("completed");
                    if (completedStr != null && !completedStr.isBlank()) 
                    {
                        for (String c : completedStr.split(";")) 
                        {
                            s.addCompleted(c.trim());
                        }
                    }
                    return Optional.of(s);
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    //not implemented yet (future use)
    public void savePlan(String id, DegreePlan plan) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}