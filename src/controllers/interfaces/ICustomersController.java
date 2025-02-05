package src.controllers.interfaces;

public interface ICustomersController {
    boolean login(String email, String password);
    boolean register(String name, String phone, String email, String password);
}
