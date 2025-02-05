package src.controllers.interfaces;

public interface IAdminController {
    void authenticateAdmin(String adminName, String adminPassword);
    void displayAdminMenu();
    void manageProducts();
}
