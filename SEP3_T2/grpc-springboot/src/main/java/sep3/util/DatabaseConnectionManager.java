package sep3.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {

    // Database connection details
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=yapper_database";
    private static final String USER = "postgres";
    private static final String PASSWORD = "via";

    // Static block to register the PostgreSQL driver
    static {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Returns a connection to the PostgreSQL database.
     *
     * @return A Connection object to the PostgreSQL database.
     * @throws SQLException If there is an issue connecting to the database.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}