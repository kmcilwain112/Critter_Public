
package Critter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kmcil
 */
public class DatabaseConnection {
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        String dbURL = "jdbc:mysql://localhost:3306/critter";
        String username = "test";
        String password = "1234";
        Connection connection = DriverManager.getConnection(dbURL, username, password);
        return connection;
    }
}
