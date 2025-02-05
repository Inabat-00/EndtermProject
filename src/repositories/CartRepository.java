package src.repositories;

import src.repositories.interfaces.ICartRepository;

public class CartRepository implements ICartRepository {
    @Override
    public void addProductToCart(int productId, int customerId, int quantity) {
        // Add product to cart logic
    }

    @Override
    public void viewCart(int customerId) {
        // View cart logic
    }

    @Override
    public void removeProductFromCart(int productId, int customerId) {
        // Remove product from cart logic
    }
}