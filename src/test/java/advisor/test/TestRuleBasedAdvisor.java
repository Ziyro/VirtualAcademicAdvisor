/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 //Unit test for RuleBasedAdvisor using JUnit
package advisor.test;

 import advisor.model.Student;
 import advisor.repo.CourseRepository;
import advisor.service.RuleBasedAdvisor;
import advisor.service.AdvisorService;
 import advisor.model.Recommendation;
 import java.sql.*;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

//tests the recmendation logc for eligble courses
public class TestRuleBasedAdvisor 
{
    private static Connection conn;
    private static CourseRepository repo;
    private static AdvisorService advisor;

    @BeforeAll
    public static void setup() throws Exception 
    {
        //inmemory DB for testing
        conn = DriverManager.getConnection("jdbc:derby:memory:testDB_Advisor;create=true");
        conn.createStatement().executeUpdate("""
            CREATE TABLE Courses (
                code VARCHAR(10) PRIMARY KEY,
                name VARCHAR(100),
                points INT,
                prereqs VARCHAR(255)
            )
        """);

        //seed a few courses
        Statement st = conn.createStatement();
        st.executeUpdate("INSERT INTO Courses VALUES ('COMP501','Programming Fundamentals',15,'')");
        st.executeUpdate("INSERT INTO Courses VALUES ('COMP502','Object Oriented Programming',15,'COMP501')");
        st.executeUpdate("INSERT INTO Courses VALUES ('COMP503','Data Structures',15,'COMP501')");
        st.executeUpdate("INSERT INTO Courses VALUES ('COMP504','Algorithms and Software Design',15,'COMP502;COMP503')");

        repo = new CourseRepository(conn);
        advisor = new RuleBasedAdvisor(repo);
    }

    @Test
    public void testRecommendationsBasedOnCompletedCourses() throws Exception 
    {
        Student s = new Student("S001", "Alice", 8.0, "Graduate soon");
        s.addCompleted("COMP501");

        List<Recommendation> recs = advisor.recommendNextSemester(s);

        assertNotNull(recs, "Recommendations list should not be null");
        assertTrue(recs.stream().anyMatch(r -> r.getCode().equals("COMP502")),
                "Should recommend COMP502 after completing COMP501");
    }

    @Test
    public void testNoEligibleCoursesWhenAllDone() throws Exception 
    {
        Student s = new Student("S002", "Bob", 7.0, "Graduate soon");
        s.addCompleted("COMP501");
        s.addCompleted("COMP502");
        s.addCompleted("COMP503");
        s.addCompleted("COMP504");

        List<Recommendation> recs = advisor.recommendNextSemester(s);

        assertEquals(1, recs.size());
        assertEquals("-", recs.get(0).getCode(), "Should show no eligible courses");
    }

    @AfterAll
    public static void tearDown() throws Exception 
    {
        conn.close();
    }
}
