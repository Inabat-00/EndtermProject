package src.models;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private int customerId;
    private Cart cart;

    public Menu(int customerId) {
        this.customerId = customerId;
        this.cart = new Cart(customerId);
    }

    public void displayMenu(String url, String user, String password) {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            try {
                System.out.println("\n✨ Welcome to the Shopping System! ✨");
                System.out.println("📌 Choose an option:");
                System.out.println("1️⃣ View Products 🛍️");
                System.out.println("2️⃣ Add Product to Cart 🛒");
                System.out.println("3️⃣ Exit 🚪");
                System.out.println("4️⃣ Search Products 🔎");
                System.out.println("5️⃣ View Cart 📋");
                System.out.print("👉 Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> viewProducts(url, user, password);
                    case 2 -> addToCart(url, user, password, scanner);
                    case 3 -> {
                        System.out.println("👋 Exiting menu... Thank you for shopping with us! Have a great day! 😊");
                        keepRunning = false;
                    }
                    case 4 -> searchProducts(url, user, password, scanner);
                    case 5 -> cart.viewCart(url, user, password);
                    default -> System.out.println("⚠️ Invalid choice! Please enter a number between 1 and 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("🚫 Invalid input! Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    private void viewProducts(String url, String user, String password) {
        String query = "SELECT * FROM products LIMIT 10";

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

    private void addToCart(String url, String user, String password, Scanner scanner) {
        viewProducts(url, user, password);
        System.out.print("\n🛍️ Enter the product ID to add to your cart: ");
        int productId = scanner.nextInt();
        System.out.print("🔢 Enter the quantity: ");
        int quantity = scanner.nextInt();
        cart.addToCart(url, user, password, productId, quantity);
    }

    private void searchProducts(String url, String user, String password, Scanner scanner) {
        System.out.print("🔎 Enter the product name or keyword to search: ");
        String keyword = scanner.nextLine().trim();

        String query = "SELECT * FROM products WHERE name ILIKE ? LIMIT 10";

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
