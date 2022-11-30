package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/***
 * class creates the connection methods for connecting to the mySQL database.
 */
public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//localhost:3306/";
    private static final String dbName = "client_schedule";

    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    private static final String MYSQLJBCDriver = "com.mysql.cj.jdbc.Driver";

    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";
    private static Connection conn = null;

    /**
     * method creates the initial connection for the mySQL database.
     * @return connection
     */
    public static Connection startConnection() {
        try{
            Class.forName(MYSQLJBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("connection successful");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return conn;
    }

    /**
     * method is used be methods requiring connection to call SQL statements for data transfer.
     * @return connection
     */
    public static Connection getConnection() {
        return conn;
    }


    /**
     * method is used to close the connection between the database and application.
     */
    public static void closeConnection(){
        try {
            conn.close();
            System.out.println("connection closed");
        } catch (SQLException e) {
            //don't care
        }
    }
}
