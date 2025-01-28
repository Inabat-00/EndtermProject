import java.util.*;
import java.util.concurrent.TimeUnit;

class Product {
    private int id;
    private String name;
    private String description;
    private double price;

    public Product(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%d. %s - %s - %.2f KZT", id, name, description, price);
    }
}

class Cart {
    private List<Product> items = new ArrayList<>();
    private double totalPrice;

    public void addToCart(Product product) {
        items.add(product);
        totalPrice += product.getPrice();
    }

    public void removeFromCart(int productId) {
        for (Iterator<Product> iterator = items.iterator(); iterator.hasNext();) {
            Product product = iterator.next();
            if (product.getId() == productId) {
                totalPrice -= product.getPrice();
                iterator.remove();
                System.out.println("Removed from cart: " + product);
                return;
            }
        }
        System.out.println("Product not found in cart.");
    }

    public void showCart() {
        System.out.println("\nYour Cart:");
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            for (Product product : items) {
                System.out.println(product);
            }
            System.out.println("Total Price: " + totalPrice + " KZT");
        }
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}

public class OnlineStore {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Product> products = Arrays.asList(
                new Product(1, "Smartphone", "Latest model with 6GB RAM and 128GB storage
//короче тут дается выбор большой там товаров и тд и там выбирвешь в корзину омжно добавить и после этого там чекаут есть и там выбираешь выбор оплаты там любой и там надо написать код свой и цифры