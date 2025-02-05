package src.controllers;

import src.controllers.interfaces.IMenuController;
import src.repositories.interfaces.IMenuRepository;

import java.util.Scanner;

public class MenuController implements IMenuController {
    private final IMenuRepository menuRepository;
    private final Scanner scanner;

    public MenuController(IMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void displayMenu() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("\n📌 Choose an option:");
            System.out.println("1️⃣ View Products 🛍️");
            System.out.println("2️⃣ Search Products 🔎");
            System.out.println("3️⃣ Exit 🚪");
            System.out.print("👉 Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> menuRepository.viewProducts();
                case 2 -> {
                    System.out.print("🔎 Enter product name to search: ");
                    String keyword = scanner.nextLine();
                    searchProducts(keyword);
                }
                case 3 -> {
                    System.out.println("👋 Exiting menu... Thank you for visiting!");
                    keepRunning = false;
                }
                default -> System.out.println("⚠️ Invalid choice! Please enter a number between 1 and 3.");
            }
        }
    }

    @Override
    public void searchProducts(String keyword) {
        menuRepository.searchProducts(keyword);
    }
}
