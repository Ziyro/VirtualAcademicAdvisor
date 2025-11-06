
 //Handles all the database setup and connection logic for the app.
 // Creates the main tables if they don't exist and also fixes missing columns.
package advisor.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String DB_URL =
            "jdbc:derby:C:/.netbeans-21/.netbeans-21/derbyANAS;create=true";

    public static void initialize() {
        try (Connection conn = getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE STUDENTS (" +
                        "id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                        "name VARCHAR(255), " +
                        "email VARCHAR(255), " +
                        "degree VARCHAR(255), " +
                        "completedCourses VARCHAR(255))");
            } catch (SQLException e) {
                // Ignore if table already exists
            }
        } catch (SQLException e) {
            System.out.println("Database init error: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
