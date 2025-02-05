package src.controllers;

import java.util.List;
import java.util.Scanner;
import src.controllers.interfaces.IReviewController;
import src.models.Review;
import src.repositories.interfaces.IReviewRepository;

public class ReviewController implements IReviewController {
    private final IReviewRepository reviewRepository;

    public ReviewController(IReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void addReview() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter your rating (1-5): ");
        double rating = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter your comment: ");
        String comment = scanner.nextLine();
        System.out.print("Enter your customer ID: ");
        int customerId = scanner.nextInt();

        Review review = new Review(productId, customerId, rating, comment);
        reviewRepository.addReview(review);
        System.out.println("Thank you for your review!");
    }

    @Override
    public void viewReviews(int productId) {
        List<Review> reviews = reviewRepository.getReviewsByProduct(productId);
        if (reviews.isEmpty()) {
            System.out.println("No reviews available for this product.");
        } else {
            System.out.println("Reviews for product ID " + productId + ":");
            for (Review review : reviews) {
                System.out.println("Rating: " + review.getRating() + " | Comment: " + review.getComment());
            }
        }
    }
}
