package src.controllers.interfaces;

public interface ICartController {
    void addProductToCart(int productId, int quantity);
    void viewCart();
    void removeProductFromCart(int productId);
}
