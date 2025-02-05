package src.controllers;

import java.util.Scanner;
import src.controllers.interfaces.IReviewController;

public class MenuController {
    private final IReviewController reviewController;

    public MenuController(IReviewController reviewController) {
        this.reviewController = reviewController;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Leave a review");
            System.out.println("2. View product reviews");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    reviewController.addReview();
                    break;
                case 2:
                    System.out.print("Enter product ID: ");
                    int productId = scanner.nextInt();
                    reviewController.viewReviews(productId);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid input! Try again.");
            }
        }
    }
}
