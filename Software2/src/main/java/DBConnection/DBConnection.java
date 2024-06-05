package DBConnection;


import java.sql.*;

/**
 * This class contains logic for opening the connection to the database
 */

public abstract class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    /**
     * This method opens the connection to the database
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //System.out.println("Error:" + e.getMessage());
        }

    }

    /**
     * This method grabs the database connection for prepared statements
     * @return connection
     */
    public static PreparedStatement getConnection(String sqlStatement) throws SQLException {
        //take in a string and return a prepared statement
        return connection.prepareStatement(sqlStatement);
    }
    public static PreparedStatement getConnectionWithGenKeys(String sqlStatement) throws SQLException {
        return connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
    }

    /**
     * This method closes the connection to the database
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

}
