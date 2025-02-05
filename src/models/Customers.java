package src.models;

import java.sql.*;
import java.util.Scanner;

public class Customers {
    private int customerId;

    public int getCustomerId() {
        return customerId;
    }

    public boolean start(String url, String user, String password) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello, welcome to the online store!");
        System.out.println("Do you have an account? (yes/no)");
        String hasAccount = scanner.nextLine().trim().toLowerCase();

        if (hasAccount.equals("yes")) {
            return login(scanner, url, user, password);
        } else if (hasAccount.equals("no")) {
            return register(scanner, url, user, password);
        } else {
            System.out.println("Invalid input. Please restart the program and enter 'yes' or 'no'.");
            return false;
        }
    }

    private boolean login(Scanner scanner, String url, String user, String password) {
        System.out.println("Please enter your email:");
        String email = scanner.nextLine().trim();
        System.out.println("Please enter your password:");
        String pass = scanner.nextLine().trim();

        String query = "SELECT * FROM customers WHERE email = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                customerId = resultSet.getInt("customer_id");
                System.out.println("You have successfully logged in! Welcome back, " + resultSet.getString("name") + "!");
                return true;
            } else {
                System.out.println("No such account exists. Please try again.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error during login:");
            e.printStackTrace();
            return false;
        }
    }

    private boolean register(Scanner scanner, String url, String user, String password) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter your phone number: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Create a password: ");
        String pass = scanner.nextLine().trim();

        String insertQuery = "INSERT INTO customers (name, phone, email, password) VALUES (?, ?, ?, ?)";
        String selectQuery = "SELECT customer_id FROM customers WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {


            insertStatement.setString(1, name);
            insertStatement.setString(2, phone);
            insertStatement.setString(3, email);
            insertStatement.setString(4, pass);
            insertStatement.executeUpdate();


            selectStatement.setString(1, email);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    customerId = resultSet.getInt("customer_id");
                    System.out.println("Registration successful! Welcome, " + name + "!");
                    return true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error during registration:");
            e.printStackTrace();
            return false;
        }

        return false;
    }
}
