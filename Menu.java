import java.sql.*;
import java.util.Scanner;

class Menu {
    private int customerId; // Customer ID for cart operations
    private Cart cart; // Instance of the Cart class

    public Menu(int customerId) {
        this.customerId = customerId;
        this.cart = new Cart(customerId); // Initialize the Cart class
    }

    public void displayMenu(String url, String user, String password) {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true; // Flag to control the menu loop

        while (keepRunning) {
            System.out.println("\nMenu:");
            System.out.println("1. View Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. Exit");
            System.out.println("4. Search Products by Name");
            System.out.println("5. View Cart");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1 -> {
                    viewProducts(url, user, password);
                    promptReturnToMenu(scanner);
                }
                case 2 -> {
                    viewProducts(url, user, password); // Display products before adding to cart
                    System.out.print("\nEnter the product ID to add to your cart: ");
                    int productId = scanner.nextInt();
                    System.out.print("Enter the quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    cart.addToCart(url, user, password, productId, quantity);
                    promptReturnToMenu(scanner);
                }
                case 3 -> {
                    System.out.println("Exiting menu... Thank you for shopping with us!");
                    keepRunning = false; // Exit the menu loop
                }
                case 4 -> {
                    searchProducts(url, user, password, scanner);
                    promptReturnToMenu(scanner);
                }
                case 5 -> {
                    cart.viewCart(url, user, password); // View the cart using the Cart class
                    promptReturnToMenu(scanner);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewProducts(String url, String user, String password) {
        String query = "SELECT * FROM products LIMIT 10";

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

    private void searchProducts(String url, String user, String password, Scanner scanner) {
        System.out.print("Enter the product name or keyword to search: ");
        String keyword = scanner.nextLine().trim();

        String query = "SELECT * FROM products WHERE name ILIKE ? LIMIT 10";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + keyword + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("\nSearch Results:");
                boolean found = false;
                while (resultSet.next()) {
                    found = true;
                    System.out.printf("ID: %d, Name: %s, Category: %s, Price: %.2f\n",
                            resultSet.getInt("product_id"),
                            resultSet.getString("name"),
                            resultSet.getString("category"),
                            resultSet.getDouble("price"));
                }
                if (!found) {
                    System.out.println("No products found matching your search.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching products: " + e.getMessage());
        }
    }

    private void promptReturnToMenu(Scanner scanner) {
        System.out.println("\nPress Enter to return to the menu...");
        scanner.nextLine(); // Wait for the user to press Enter
    }
}
