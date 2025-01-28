import java.sql.*;
import java.util.Scanner;

class Admin {
    public boolean start(String url, String user, String password) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter admin username:");
            String adminName = scanner.nextLine().trim();
            System.out.println("Enter admin password:");
            String adminPassword = scanner.nextLine().trim();

            String query = "SELECT * FROM admin WHERE name = ? AND password = ?";

            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, adminName);
                preparedStatement.setString(2, adminPassword);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    System.out.println("You have successfully logged in as an administrator. Welcome, " + adminName + "!");
                    adminMenu(connection); // Open admin menu
                    return true;
                } else {
                    System.out.println("Invalid username or password.");
                    System.out.println("Do you want to try again? (yes/no)");
                    String retry = scanner.nextLine().trim().toLowerCase();

                    if (retry.equals("no")) {
                        System.out.println("Exiting admin login...");
                        return false;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Connection error: " + e.getMessage());
                return false;
            }
        }
    }

    private void adminMenu(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Add Product");
            System.out.println("3. Edit Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    viewProducts(connection);
                    break;
                case 2:
                    addProduct(connection, scanner);
                    break;
                case 3:
                    editProduct(connection, scanner);
                    break;
                case 4:
                    deleteProduct(connection, scanner);
                    break;
                case 5:
                    System.out.println("Exiting admin menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void viewProducts(Connection connection) {
        String query = "SELECT * FROM products";

        try (Statement statement = connection.createStatement();
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

    private void addProduct(Connection connection, Scanner scanner) {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter product category: ");
        String category = scanner.nextLine().trim();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        String query = "INSERT INTO products (name, category, price) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, category);
            preparedStatement.setDouble(3, price);
            preparedStatement.executeUpdate();

            System.out.println("Product added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    private void editProduct(Connection connection, Scanner scanner) {
        System.out.print("Enter the product ID to edit: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter new product name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter new product category: ");
        String category = scanner.nextLine().trim();
        System.out.print("Enter new product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        String query = "UPDATE products SET name = ?, category = ?, price = ? WHERE product_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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

    private void deleteProduct(Connection connection, Scanner scanner) {
        System.out.print("Enter the product ID to delete: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        String query = "DELETE FROM products WHERE product_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();

            System.out.println("Product deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
    }
}
