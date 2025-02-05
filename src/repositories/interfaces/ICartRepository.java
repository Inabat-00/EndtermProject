package src.repositories.interfaces;

public interface ICartRepository {
    void addProductToCart(int productId, int customerId, int quantity);
    void viewCart(int customerId);
    void removeProductFromCart(int productId, int customerId);
}
