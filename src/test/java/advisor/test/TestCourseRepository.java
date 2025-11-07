// Unit test for CourseRepository using JUnit
package advisor.test;

import advisor.model.Course;
import advisor.repo.CourseRepository;
 import java.sql.*;
 import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

//tests inserting and finding courses
public class TestCourseRepository 
{
    private static Connection conn;
    private static CourseRepository repo;

    @BeforeAll
    public static void setup() throws Exception 
    {
        //create an in-memory Derby database
        conn = DriverManager.getConnection("jdbc:derby:memory:testDB_Courses;create=true");
        conn.createStatement().executeUpdate("""
            CREATE TABLE Courses (
                code VARCHAR(10) PRIMARY KEY,
                name VARCHAR(100),
                points INT,
                prereqs VARCHAR(255)
            )
        """);
        repo = new CourseRepository(conn);
    }

    @Test
    public void testInsertAndFindCourse() 
    {
        Course c = new Course("COMP501", "Programming Fundamentals", 15, List.of());
        repo.insert(c);

        var found = repo.findByCode("COMP501");
        assertTrue(found.isPresent(), "Course should exist");
        assertEquals("Programming Fundamentals", found.get().getName());
    }

    @Test
    public void testFindAllCourses() throws Exception 
    {
        Course c1 = new Course("COMP502", "OOP", 15, List.of("COMP501"));
        repo.insert(c1);

        List<Course> all = repo.findAll();
        assertFalse(all.isEmpty(), "Courses list should not be empty");
    }

    @AfterAll
    public static void tearDown() throws Exception 
    {
        conn.close();
    }
}
