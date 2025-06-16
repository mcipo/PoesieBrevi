package controller;

import entity.User;
import database.DAO.UserDAO;

public class LoginController {
    public static User autenticaUtente(String email, String password) {
        User user = UserDAO.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    public static boolean esisteUtente(String email) {
        return UserDAO.getUserByEmail(email) != null;
    }


    public static boolean verificaCredenziali(String email, String password) {
        return autenticaUtente(email, password) != null;
    }

    public static boolean isAdmin(String email, String password) {
        User user = autenticaUtente(email, password);
        return user != null && user.isAdmin();
    }
}