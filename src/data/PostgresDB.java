package src.data;

import src.data.interfaces.IDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    private final String url;
    private final String user;
    private final String password;

    public PostgresDB(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }
}
