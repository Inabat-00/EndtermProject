package src.controllers;

import src.controllers.interfaces.ICustomersController;

public class CustomersController implements ICustomersController {
    @Override
    public boolean login(String email, String password) {
        // Login logic
        return true;
    }

    @Override
    public boolean register(String name, String phone, String email, String password) {
        // Register logic
        return true;
    }
}