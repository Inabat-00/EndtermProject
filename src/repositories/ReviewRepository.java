package src.repositories;

import src.data.interfaces.IDB;
import src.models.Review;
import src.repositories.interfaces.IReviewRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository implements IReviewRepository {
    private final IDB db;

    public ReviewRepository(IDB db) {
        this.db = db;
    }

    @Override
    public void addReview(Review review) {
        try (Connection con = db.getConnection()) {
            String sql = "INSERT INTO reviews (product_id, customer_id, rating, comment) VALUES (?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, review.getProductId());
            st.setInt(2, review.getCustomerId());
            st.setDouble(3, review.getRating());
            st.setString(4, review.getComment());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getAverageRating(int productId) {
        double avgRating = 0.0;
        try (Connection con = db.getConnection()) {
            String sql = "SELECT COALESCE(AVG(rating), 0.0) AS average_rating FROM reviews WHERE product_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, productId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                avgRating = rs.getDouble("average_rating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avgRating;
    }

    @Override
    public List<Review> getReviewsByProduct(int productId) {
        List<Review> reviews = new ArrayList<>();
        try (Connection con = db.getConnection()) {
            String sql = "SELECT * FROM reviews WHERE product_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, productId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Review review = new Review(
                    rs.getInt("product_id"),
                    rs.getInt("customer_id"),
                    rs.getDouble("rating"),
                    rs.getString("comment")
                );
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
