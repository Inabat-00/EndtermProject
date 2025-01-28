import java.sql.*;
import java.util.Scanner;

public class Customers {
    public void start(String url, String user, String password) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello, welcome to the Meshki online store!");
        System.out.println("Do you have an account? (yes/no)");
        String hasAccount = scanner.nextLine().trim().toLowerCase();

        if (hasAccount.equals("yes")) {
            login(scanner, url, user, password);
        } else if (hasAccount.equals("no")) {
            register(scanner, url, user, password);
        } else {
            System.out.println("Invalid input. Please restart the program and enter 'yes' or 'no'.");
        }

        scanner.close();
    }

    private void login(Scanner scanner, String url, String user, String password) {
        System.out.println("Please enter your email:");
        String email = scanner.nextLine().trim();
        System.out.println("Please enter your password:");
        String pass = scanner.nextLine().trim();

        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("You have successfully logged in! Welcome back, " + resultSet.getString("name") + "!");
            } else {
                System.out.println("No such account exists. Please try again.");
            }

        } catch (SQLException e) {
            System.out.println("Error during login:");
            e.printStackTrace();
        }
    }

    private void register(Scanner scanner, String url, String user, String password) {
        System.out.println("Please enter your name:");
        String name = scanner.nextLine().trim();
        System.out.println("Please enter your phone number:");
        String phone = scanner.nextLine().trim();
        System.out.println("Please enter your email:");
        String email = scanner.nextLine().trim();
        System.out.println("Please create a password:");
        String pass = scanner.nextLine().trim();

        String insertQuery = "INSERT INTO users (name, phone, email, password) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, pass);
            preparedStatement.executeUpdate();

            System.out.println("Registration successful! Welcome, " + name + "!");

        } catch (SQLException e) {
            System.out.println("Error during registration:");
            e.printStackTrace();
        }
    }
}


