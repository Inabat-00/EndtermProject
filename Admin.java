import java.sql.*;
import java.util.Scanner;

class Admin {
    public void start(String url, String user, String password) {
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
                    System.out.println("You are logged in as an administrator. Welcome, " + adminName + "!");
                    break;
                } else {
                    System.out.println("Invalid username or password.");
                    System.out.println("Do you want to try again? (yes/no)");
                    String retry = scanner.nextLine().trim().toLowerCase();

                    if (retry.equals("no")) {
                        System.out.println("Exiting admin login...");
                        break;
                    }
                }

            } catch (SQLException e) {
                System.out.println("Connection error: " + e.getMessage());
                break;
            }
        }

        scanner.close();
    }
}
