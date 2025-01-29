import java.sql.*;
import java.util.Scanner;

class Cart {
    private int customerId;

    public Cart(int customerId) {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Invalid customer ID. Please log in again.");
        }
        this.customerId = customerId;
    }

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

    public void viewCart(String url, String user, String password) {
        String query = """
    SELECT c.id, p.name, p.price, c.quantity, (p.price * c.quantity) AS total_price
    FROM cart c
    JOIN products p ON c.product_id = p.product_id
    WHERE c.customer_id = ?
""";


        double totalAmount = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, customerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("\nYour Cart:");
                boolean hasItems = false;
                while (resultSet.next()) {
                    hasItems = true;
                    String productName = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");
                    double totalPrice = resultSet.getDouble("total_price");

                    totalAmount += totalPrice;

                    System.out.printf("Product: %s, Price: %.2f KZT, Quantity: %d, Total: %.2f KZT\n",
                            productName, price, quantity, totalPrice);
                }

                if (!hasItems) {
                    System.out.println("Your cart is empty.");
                    return;
                }

                System.out.printf("\nTotal amount: %.2f KZT\n", totalAmount);
                Scanner scanner = new Scanner(System.in);
                System.out.print("Would you like to place an order for this amount? (yes/no): ");
                String response = scanner.nextLine().trim().toLowerCase();

                if (response.equals("yes")) {
                    placeOrder(url, user, password, totalAmount);
                }

            }
        } catch (SQLException e) {
            System.out.println("Error retrieving cart items: " + e.getMessage());
        }
    }

    private void placeOrder(String url, String user, String password, double totalAmount) {
        String insertOrderQuery = "INSERT INTO orders (customer_id, order_date, total_amount) VALUES (?, NOW(), ?)";
        String clearCartQuery = "DELETE FROM cart WHERE customer_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement orderStatement = connection.prepareStatement(insertOrderQuery);
             PreparedStatement clearCartStatement = connection.prepareStatement(clearCartQuery)) {

            orderStatement.setInt(1, customerId);
            orderStatement.setDouble(2, totalAmount);
            orderStatement.executeUpdate();

            clearCartStatement.setInt(1, customerId);
            clearCartStatement.executeUpdate();

            System.out.println("Order placed successfully! Your cart has been cleared.");
        } catch (SQLException e) {
            System.out.println("Error placing order: " + e.getMessage());
        }
    }

    public void removeFromCart(String url, String user, String password, int productId) {
        String query = "DELETE FROM cart WHERE customer_id = ? AND product_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, productId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product removed from your cart.");
            } else {
                System.out.println("Product not found in your cart.");
            }

        } catch (SQLException e) {
            System.out.println("Error removing product from cart: " + e.getMessage());
        }
    }
}
