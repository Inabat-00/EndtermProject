package src.repositories.interfaces;

public interface IAdminRepository {
    boolean authenticateAdmin(String adminName, String adminPassword);
    void addProduct(String name, String category, double price);
    void removeProduct(int productId);
}
