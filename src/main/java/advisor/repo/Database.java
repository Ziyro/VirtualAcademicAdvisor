/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.repo;

import java.sql.*;

//sets up and connects to the database
public class Database 
{

    //update path if DB location differs
    private static final String DB_URL = 
          "jdbc:derby:C:/.netbeans-21/.netbeans-21/derbyANAS;create=true";

    //returns a connection to the db
    public static Connection getConnection() throws SQLException 
    {
        return DriverManager.getConnection(DB_URL);
    }

    //creates tables, adds missing columns, and seeds courses 
    public static void initialize() 
    {
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) 
        {
            //create Students table with completed column
            try 
            {
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
            catch (SQLException e)
            {
                if (!"X0Y32".equals(e.getSQLState())) throw e; //table already exists

                //if table exists, make sure 'completed' column also exists
                try 
                {
                    boolean hasCompleted = false;
                    ResultSet rs = conn.getMetaData().getColumns(null, null, "STUDENTS", null);
                    while (rs.next()) 
                    {
                        if ("COMPLETED".equalsIgnoreCase(rs.getString("COLUMN_NAME"))) 
                        {
                            hasCompleted = true;
                            break;
                        }
                    }
                    rs.close();

                    //add the column if missing
                    if (!hasCompleted) 
                    {
                        System.out.println("Adding missing 'completed' column to Students table...");
                        st.executeUpdate("ALTER TABLE Students ADD COLUMN completed VARCHAR(255)");
                    }
                } 
                catch (SQLException inner) 
                {
                    System.err.println("Error checking/adding 'completed' column: " + inner.getMessage());
                }
            }

            //create Courses table
            try 
            {
                st.executeUpdate("""
                    CREATE TABLE Courses (
                        code VARCHAR(10) PRIMARY KEY,
                        name VARCHAR(100),
                        points INT,
                        prereqs VARCHAR(255)
                    )
                """);
            } 
            catch (SQLException e) 
            {
                if (!"X0Y32".equals(e.getSQLState())) throw e; //table already exists
            }

            //check if courses table is empty and seed if needed
            try (ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM Courses")) 
            {
                rs.next();
                if (rs.getInt(1) == 0)
                {
                    st.executeUpdate("""
                        INSERT INTO Courses (code, name, points, prereqs) VALUES
                        ('COMP501','Programming Fundamentals',15,''),
                        ('COMP502','Object-Oriented Programming',15,'COMP501'),
                        ('COMP503','Data Structures',15,'COMP501'),
                        ('COMP504','Algorithms and Software Design',15,'COMP502;COMP503'),
                        ('MATH501','Discrete Mathematics',15,''),
                        ('MATH502','Statistics for Computing',15,'MATH501'),
                        ('ENGR501','Engineering Principles',15,''),
                        ('ENGR502','Embedded Systems',15,'ENGR501'),
                        ('GENED101','Communication Skills',15,''),
                        ('GENED102','Professional Practice',15,'')
                    """);
                    System.out.println("Courses table seeded.");
                }
            }

            System.out.println("Database initialized.");
        } 
        catch (SQLException e) 
        {
            System.err.println("Database init error: " + e.getMessage());
        }
    }
}
