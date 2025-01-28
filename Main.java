import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/shop";
        String user = "postgres";
        String password = "postgres";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you an admin or a customer? (admin/customer): ");
        String role = scanner.nextLine().trim().toLowerCase();

        if (role.equals("admin")) {
            Admin admin = new Admin();
            if (admin.start(url, user, password)) {
                Menu menu = new Menu();
                menu.displayMenu(url, user, password);
            }
        } else if (role.equals("customer")) {
            Customers customers = new Customers();
            if (customers.start(url, user, password)) {
                Menu menu = new Menu();
                menu.displayMenu(url, user, password);
            }
        } else {
            System.out.println("Invalid input. Program terminated.");
        }
    }
}
