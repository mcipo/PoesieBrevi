package controller;

import entity.User;
import entity.Profilo;
import database.DAO.UserDAO;

public class RegistrazioneController {


    public static User creaUtenteInMemoria(String nome, String cognome, String email, String password, boolean isAdmin) {
        Profilo profilo = new Profilo("", "", "", null);

        return new User(password, email, nome, cognome, isAdmin, profilo);
    }
    

    public static boolean salvaUtente(User user) {
        if (user == null) {
            return false;
        }
        if (UserDAO.getUserByEmail(user.getEmail()) != null) {
            return false;
        }
        
        return UserDAO.addUser(user);
    }

    public static boolean esisteUtente(String email) {
        return UserDAO.getUserByEmail(email) != null;
    }
}