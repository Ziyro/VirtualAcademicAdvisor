/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// Unit test for StudentRepository using JUnit 5
package advisor.test;

import advisor.model.Student;
import advisor.repo.StudentRepository;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//tests inserting, fetching, and listing students from in-memory DB
public class TestStudentRepository {

    //creates a fresh in-memory DB schema before each test
    private Connection newMemDb() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:derby:memory:studentTestDb;create=true");
        try (Statement st = conn.createStatement()) {
            //drop old table if it exists
            try {
                st.executeUpdate("DROP TABLE Students");
            } catch (SQLException ignore) {
                //ignore if table doesn't exist
            }

            //create new table with completed column
            st.executeUpdate("""
                CREATE TABLE Students (
                    id VARCHAR(20) PRIMARY KEY,
                    name VARCHAR(100),
                    gpa DOUBLE,
                    goal VARCHAR(200),
                    completed VARCHAR(255)
                )
            """);
        }
        return conn;
    }

    @Test
    public void insertAndFetchStudent_withCompletedCourses() throws Exception {
        try (Connection conn = newMemDb()) {
            StudentRepository repo = new StudentRepository(conn);

            //create student and add completed courses
            Student s = new Student("S100", "Alice Example", 7.2, "Finish soon");
            s.addCompleted("COMP501");
            s.addCompleted("COMP502");

            //insert into DB
            repo.upsert(s);

            //fetch from DB
            Optional<Student> got = repo.findById("S100");
            assertTrue(got.isPresent(), "Student should exist in DB");
            assertEquals("Alice Example", got.get().getName());
            assertEquals(7.2, got.get().getGpa(), 1e-9);
            assertTrue(got.get().getCompleted().contains("COMP501"));
            assertTrue(got.get().getCompleted().contains("COMP502"));

            //update student record
            got.get().setName("Alice E.");
            got.get().addCompleted("COMP503");
            repo.upsert(got.get());

            Optional<Student> got2 = repo.findById("S100");
            assertTrue(got2.isPresent(), "Updated student should still exist");
            assertEquals("Alice E.", got2.get().getName());
            assertTrue(got2.get().getCompleted().contains("COMP503"));
        }
    }

    @Test
    public void findAllReturnsMultipleStudents() throws Exception {
        try (Connection conn = newMemDb()) {
            StudentRepository repo = new StudentRepository(conn);

            //insert two students
            Student s1 = new Student("S200", "John Doe", 6.5, "Pass COMP504");
            Student s2 = new Student("S201", "Jane Roe", 8.1, "Graduate Soon");
            repo.upsert(s1);
            repo.upsert(s2);

            //verify two records returned
            List<Student> all = repo.findAll();
            assertNotNull(all, "List should not be null");
            assertEquals(2, all.size(), "Should return two students");
        }
    }
}
