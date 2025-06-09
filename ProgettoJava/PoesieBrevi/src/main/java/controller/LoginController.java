package controller;

import entity.User;
import database.DAO.UserDAO;

public class LoginController {
    private final UserDAO userDAO;

    public LoginController() {
        this.userDAO = new UserDAO();
    }
    public User autenticaUtente(String email, String password) {
        User user = userDAO.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    public boolean esisteUtente(String email) {
        return userDAO.getUserByEmail(email) != null;
    }


    public boolean verificaCredenziali(String email, String password) {
        return autenticaUtente(email, password) != null;
    }

    public boolean isAdmin(String email, String password) {
        User user = autenticaUtente(email, password);
        return user != null && user.isAdmin();
    }
}