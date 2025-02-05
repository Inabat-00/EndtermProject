package src.models;

import java.sql.Timestamp;

public class Review {
    private int reviewId;
    private int productId;
    private int customerId;
    private double rating;
    private String comment;
    private Timestamp createdAt;

    // Конструктор
    public Review(int productId, int customerId, double rating, String comment) {
        this.productId = productId;
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = new Timestamp(System.currentTimeMillis()); // Устанавливаем текущую дату
    }

    // Геттеры
    public int getProductId() { return productId; }
    public int getCustomerId() { return customerId; }
    public double getRating() { return rating; }
    public String getComment() { return comment; }
    public Timestamp getCreatedAt() { return createdAt; }

    // Сеттеры (если они нужны)
    public void setProductId(int productId) { this.productId = productId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setRating(double rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
}
