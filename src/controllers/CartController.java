package src.controllers;

import src.controllers.interfaces.ICartController;

public class CartController implements ICartController {
    @Override
    public void addProductToCart(int productId, int quantity) {
        // Add product to cart logic
    }

    @Override
    public void viewCart() {
        // View cart logic
    }

    @Override
    public void removeProductFromCart(int productId) {
        // Remove product from cart logic
    }
}