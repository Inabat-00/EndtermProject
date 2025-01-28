import java.util.*;

public class Cart {
    private List<Books> items = new ArrayList<>();
    private double totalPrice;

    public void addToCart(Books book, int quantity) {
        for (int i = 0; i < quantity; i++) {
            items.add(book);
            totalPrice += book.getPrice();
        }
    }

    public void showCart() {
        for (Books item : items) {
            System.out.println(item);
        }
        System.out.println("Total Price: $" + totalPrice);
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
