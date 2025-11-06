/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advisor.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import advisor.repo.Database;

public class TestDatabaseConnection {

   @Test
public void testConnectionNotNull() throws Exception {
    assertNotNull(advisor.repo.Database.getConnection());
}

}
