package src.repositories;

import src.repositories.interfaces.ICustomersRepository;

public class CustomersRepository implements ICustomersRepository {
    @Override
    public boolean login(String email, String password) {

        return true;
    }

    @Override
    public boolean register(String name, String phone, String email, String password) {

        return true;
    }
}