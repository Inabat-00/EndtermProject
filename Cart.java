import java.sql.*;

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
            preparedStatement.setInt(2, customerId); // Use the correct customerId
            preparedStatement.setInt(3, quantity);
            preparedStatement.executeUpdate();

            System.out.println("Product successfully added to your cart!");
        } catch (SQLException e) {
            System.out.println("Error adding product to the cart: " + e.getMessage());
        }
    }

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
                    System.out.printf("Product: %s, Price: %.2f, Quantity: %d\n",

                            resultSet.getString("name"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("quantity"));
                }

                if (!hasItems) {
                    System.out.println("Your cart is empty.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving cart items: " + e.getMessage());
        }
    }
}
