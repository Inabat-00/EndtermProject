package src.repositories.interfaces;

import src.models.Review;
import java.util.List;

public interface IReviewRepository {
    void addReview(Review review);
    double getAverageRating(int productId);
    List<Review> getReviewsByProduct(int productId);
}
