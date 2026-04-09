package dam.code.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String URL = "jdbc:postgresql://localhost:5432/cinesa";
    private static final String USER = "postgres";
    private static final String PASSWORD = "posgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}