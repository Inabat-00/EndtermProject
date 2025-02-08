package src.data;

import src.data.interfaces.IDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


//Application of Design Patterns
public class PostgresDB implements IDB {
    private static PostgresDB instance; // Singleton instance
    private final String url;
    private final String user;
    private final String password;

    public PostgresDB(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public static PostgresDB getInstance(String url, String user, String password) {
        if (instance == null) {
            instance = new PostgresDB(url, user, password);
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public boolean validateAdminAccess(String adminName, String adminPassword) {
        String query = "SELECT * FROM admin WHERE name = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, adminName);
            preparedStatement.setString(2, adminPassword);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean validateCustomerAccess(int customerId) {
        String query = "SELECT * FROM customers WHERE customer_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean isValidOrderId(int orderId) {
        return orderId > 0;
    }

    public String getFullOrderDescription(int orderId) {

        if (!isValidOrderId(orderId)) {
            return "Invalid Order ID";
        }

        String query = "SELECT o.order_id, c.name AS customer_name, p.name AS product_name, oi.quantity, o.total_amount " +
                "FROM orders o " +
                "JOIN customers c ON o.customer_id = c.customer_id " +
                "JOIN order_items oi ON o.order_id = oi.order_id " +
                "JOIN products p ON oi.product_id = p.product_id " +
                "WHERE o.order_id = ?";
//Implementation of 'JOIN's
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            StringBuilder result = new StringBuilder();
            while (resultSet.next()) {
                result.append("Order ID: ").append(resultSet.getInt("order_id"))
                        .append(", Customer: ").append(resultSet.getString("customer_name"))
                        .append(", Product: ").append(resultSet.getString("product_name"))
                        .append(", Quantity: ").append(resultSet.getInt("quantity"))
                        .append(", Total Amount: ").append(resultSet.getDouble("total_amount"))
                        .append("\n");
            }
            return result.toString();
        } catch (SQLException e) {
            return "Error retrieving order details: " + e.getMessage();
        }
    }
}
