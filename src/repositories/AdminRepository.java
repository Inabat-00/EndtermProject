package src.repositories;

import src.repositories.interfaces.IAdminRepository;
import java.sql.*;

public class AdminRepository implements IAdminRepository {
    private final String url;
    private final String user;
    private final String password;

    public AdminRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public boolean authenticateAdmin(String adminName, String adminPassword) {
        String query = "SELECT * FROM admin WHERE name = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, adminName);
            preparedStatement.setString(2, adminPassword);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Егер нәтиже болса, аутентификация сәтті өтті
            }

        } catch (SQLException e) {
            System.out.println("Error authenticating admin: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void addProduct(String name, String category, double price) {
        String query = "INSERT INTO products (name, category, price) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, category);
            preparedStatement.setDouble(3, price);
            preparedStatement.executeUpdate();

            System.out.println("Product added successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    @Override
    public void removeProduct(int productId) {
        String query = "DELETE FROM products WHERE product_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("Product not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
    }
}
