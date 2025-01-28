import java.sql.*;
import java.util.Scanner;

class Cart {
    private int customerId; // The ID of the logged-in customer

    public Cart(int customerId) {
        this.customerId = customerId; // Initialize the Cart class with the logged-in customer's ID
    }

    // View items in the cart
    public void viewCart(String url, String user, String password) {
        String query = """
                SELECT c.id, p.name, p.price, c.quantity
                FROM cart c
                JOIN products p ON c.product_id = p.product_id
                WHERE c.customer_id = ?
                """;

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, customerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("\nYour Cart:");
                boolean hasItems = false;
                while (resultSet.next()) {
                    hasItems = true;
                    System.out.printf("Cart ID: %d, Product: %s, Price: %.2f, Quantity: %d\n",
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("quantity"));
                }

                if (!hasItems) {
                    System.out.println("Your cart is empty.");
                    return;
                }

                // Ask if the user wants to remove an item
                Scanner scanner = new Scanner(System.in);
                System.out.println("\nDo you want to remove an item from your cart? (yes/no)");
                String response = scanner.nextLine().trim().toLowerCase();
                if (response.equals("yes")) {
                    System.out.print("Enter the Cart ID of the item to remove: ");
                    int cartId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    removeFromCart(connection, cartId);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving cart items: " + e.getMessage());
        }
    }

    // Add an item to the cart
    public void addToCart(String url, String user, String password, int productId, int quantity) {
        String query = "INSERT INTO cart (product_id, customer_id, quantity) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.executeUpdate();

            System.out.println("Product successfully added to your cart!");
        } catch (SQLException e) {
            System.out.println("Error adding product to the cart: " + e.getMessage());
        }
    }

    // Remove an item from the cart
    private void removeFromCart(Connection connection, int cartId) {
        String query = "DELETE FROM cart WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, cartId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Item successfully removed from your cart.");
            } else {
                System.out.println("Cart ID not found. No items removed.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing item from cart: " + e.getMessage());
        }
    }
}
