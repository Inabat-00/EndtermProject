package src.controllers;

import src.controllers.interfaces.IAdminController;
import src.repositories.interfaces.IAdminRepository;

public class AdminController implements IAdminController {
    private String adminName;
    private String adminPassword;

    public AdminController(IAdminRepository adminRepo) {
    }

    @Override
    public void authenticateAdmin(String adminName, String adminPassword) {
        // Admin authentication logic
    }

    @Override
    public void displayAdminMenu() {
        // Admin menu displaying logic
    }

    @Override
    public void manageProducts() {
        // Product management logic
    }
}