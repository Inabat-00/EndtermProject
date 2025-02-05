import src.models.Admin;
import src.models.Menu;
import src.models.Customers;
import src.repositories.ReviewRepository;
import src.repositories.interfaces.IReviewRepository;
import src.controllers.ReviewController;
import src.controllers.interfaces.IReviewController;
import src.data.PostgresDB;
import src.data.interfaces.IDB;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "Takanashi_13";

        IDB db = new PostgresDB(url, user, password); // Подключение к БД
        IReviewRepository reviewRepository = new ReviewRepository(db); // Репозиторий отзывов
        IReviewController reviewController = new ReviewController(reviewRepository); // Контроллер отзывов

        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you an admin or a customer? (admin/customer): ");
        String role = scanner.nextLine().trim().toLowerCase();

        if (role.equals("admin")) {
            Admin admin = new Admin();
            admin.start(url, user, password);
        } else if (role.equals("customer")) {
            Customers customers = new Customers();
            if (customers.start(url, user, password)) {
                int customerId = customers.getCustomerId();
                Menu menu = new Menu(customerId, reviewController); // Передаем ReviewController
                menu.displayMenu(url, user, password);
            }
        } else {
            System.out.println("Invalid input. Program terminated.");
        }
    }
}
