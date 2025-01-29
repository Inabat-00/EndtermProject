package users;
import java.sql.*;
import java.util.Scanner;

public class Admin {
    public void start(String url, String user, String password) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter admin username: ");
        String adminName = scanner.nextLine().trim();
        System.out.println("Enter admin password: ");
        String adminPassword = scanner.nextLine().trim();

        if (authenticateAdmin(url, user, password, adminName, adminPassword)) {
            System.out.println("Welcome, Admin!");
            adminMenu(url, user, password);
        } else {
            System.out.println("Invalid credentials. Exiting...");
        }
    }

    private boolean authenticateAdmin(String url, String user, String password, String adminName, String adminPassword) {
        String query = "SELECT * FROM admin WHERE name = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, adminName);
            preparedStatement.setString(2, adminPassword);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            System.out.println("Error authenticating admin: " + e.getMessage());
            return false;
        }
    }

    private void adminMenu(String url, String user, String password) {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Add Product");
            System.out.println("3. Edit Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewProducts(url, user, password);
                case 2 -> addProduct(url, user, password, scanner);
                case 3 -> editProduct(url, user, password, scanner);
                case 4 -> deleteProduct(url, user, password, scanner);
                case 5 -> {
                    System.out.println("Exiting admin menu...");
                    keepRunning = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewProducts(String url, String user, String password) {
        String query = "SELECT * FROM products";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("\nAvailable Products:");
            while (resultSet.next()) {
                System.out.printf("ID: %d, Name: %s, Category: %s, Price: %.2f\n",
                        resultSet.getInt("product_id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getDouble("price"));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }
    }

    private void addProduct(String url, String user, String password, Scanner scanner) {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter product category: ");
        String category = scanner.nextLine().trim();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

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

    private void editProduct(String url, String user, String password, Scanner scanner) {
        System.out.print("Enter the product ID to edit: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new product name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter new product category: ");
        String category = scanner.nextLine().trim();
        System.out.print("Enter new product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        String query = "UPDATE products SET name = ?, category = ?, price = ? WHERE product_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, category);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, productId);
            preparedStatement.executeUpdate();

            System.out.println("Product updated successfully!");

        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }

    private void deleteProduct(String url, String user, String password, Scanner scanner) {
        System.out.print("Enter the product ID to delete: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        String query = "DELETE FROM products WHERE product_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();

            System.out.println("Product deleted successfully!");

        } catch (SQLException e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
    }
}