package src;

import src.controllers.AdminController;
import src.controllers.MenuController;
import src.controllers.interfaces.IAdminController;
import src.controllers.interfaces.IMenuController;
import src.data.PostgresDB;
import src.data.interfaces.IDB;
import src.repositories.AdminRepository;
import src.repositories.MenuRepository;
import src.repositories.interfaces.IAdminRepository;
import src.repositories.interfaces.IMenuRepository;

import java.util.Scanner;

public class MyApplication {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/shop";
        String user = "postgres";
        String password = "123456";

        IDB database = new PostgresDB(url, user, password);
        IAdminRepository adminRepo = new AdminRepository(url, user, password);
        IMenuRepository menuRepo = new MenuRepository(url, user, password);


        IAdminController adminController = new AdminController(adminRepo);
        IMenuController menuController = new MenuController(menuRepo);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the application!");
        System.out.println("Are you an admin or a customer? (admin/customer): ");
        String role = scanner.nextLine().trim().toLowerCase();

        if (role.equals("admin")) {

            System.out.println("Please log in as Admin:");
            adminController.authenticateAdmin("adminName", "adminPassword");
            adminController.manageProducts();
        } else if (role.equals("customer")) {

            System.out.println("Please log in as Customer:");
            menuController.displayMenu();
        } else {
            System.out.println("Invalid input. Please restart the application and choose 'admin' or 'customer'.");
        }
    }
}

