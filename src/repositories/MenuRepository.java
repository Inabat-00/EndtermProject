package src.repositories;

import src.repositories.interfaces.IMenuRepository;
import java.sql.*;

public class MenuRepository implements IMenuRepository {
    private final String url;
    private final String user;
    private final String password;

    public MenuRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public void viewProducts() {
        String query = "SELECT * FROM products";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("\n🌟 Available Products 🌟");
            while (resultSet.next()) {
                System.out.printf("🆔 ID: %d | 🏷️ Name: %s | 📦 Category: %s | 💰 Price: %.2f KZT\n",
                        resultSet.getInt("product_id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getDouble("price"));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error retrieving products: " + e.getMessage());
        }
    }

    @Override
    public void searchProducts(String keyword) {
        String query = "SELECT * FROM products WHERE name ILIKE ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + keyword + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("\n🔍 Search Results:");
                boolean found = false;
                while (resultSet.next()) {
                    found = true;
                    System.out.printf("🆔 ID: %d | 🏷️ Name: %s | 📦 Category: %s | 💰 Price: %.2f KZT\n",
                            resultSet.getInt("product_id"),
                            resultSet.getString("name"),
                            resultSet.getString("category"),
                            resultSet.getDouble("price"));
                }
                if (!found) {
                    System.out.println("❌ No products found matching your search.");
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error searching products: " + e.getMessage());
        }
    }
}
