package src.repositories.interfaces;

public interface ICustomersRepository {
    boolean login(String email, String password);
    boolean register(String name, String phone, String email, String password);
}
