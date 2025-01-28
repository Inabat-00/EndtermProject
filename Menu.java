import java.sql.*;
import java.util.Scanner;

class Menu {
    public void displayMenu(String url, String user, String password) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. View Products");
                System.out.println("2. Add Product to Cart");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        viewProducts(connection);
                        break;
                    case 2:
                        addToCart(connection, scanner);
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    private void viewProducts(Connection connection) {
        String query = "SELECT * FROM products LIMIT 10";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("\nAvailable Products:");
            while (resultSet.next()) {
                System.out.printf("ID: %d, Name: %s, Price: %.2f\n",
                        resultSet.getInt("product_id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }
    }

    private void addToCart(Connection connection, Scanner scanner) {
        System.out.print("\nEnter the product ID to add to your cart: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter the quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        String query = "INSERT INTO cart (product_id, quantity) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, quantity);
            preparedStatement.executeUpdate();

            System.out.println("Product successfully added to the cart!");
        } catch (SQLException e) {
            System.out.println("Error adding product to the cart: " + e.getMessage());
        }
    }
}
