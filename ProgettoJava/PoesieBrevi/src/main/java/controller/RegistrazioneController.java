package controller;

import entity.User;
import entity.Profilo;
import database.DAO.UserDAO;

public class RegistrazioneController {
    private final UserDAO userDAO;

    public RegistrazioneController() {
        this.userDAO = new UserDAO();
    }


    public User creaUtenteInMemoria(String nome, String cognome, String email, String password, boolean isAdmin) {
        Profilo profilo = new Profilo("", "", "", null);

        return new User(password, email, nome, cognome, isAdmin, profilo);
    }
    

    public boolean salvaUtente(User user) {
        if (user == null) {
            return false;
        }
        if (userDAO.getUserByEmail(user.getEmail()) != null) {
            return false;
        }
        
        return userDAO.addUser(user);
    }

    public boolean esisteUtente(String email) {
        return userDAO.getUserByEmail(email) != null;
    }
}