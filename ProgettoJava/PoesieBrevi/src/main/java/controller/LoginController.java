package controller;

import entity.User;
import database.DAO.UserDAO;

/**
 * Controller che gestisce l'autenticazione e la verifica delle credenziali degli utenti.
 * Fornisce metodi per verificare le credenziali, controllare l'esistenza di un utente
 * e identificare gli utenti con privilegi di amministratore.
 */
public class LoginController {

    private LoginController(){

    }

    /**
     * Autentica un utente verificando le credenziali fornite.
     *
     * @param email Email dell'utente.
     * @param password Password dell'utente.
     * @return Oggetto User se l'autenticazione ha successo, null altrimenti.
     */
    public static User autenticaUtente(String email, String password) {
        User user = UserDAO.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    /**
     * Verifica se esiste un utente con l'email specificata nel sistema.
     *
     * @param email Email da verificare.
     * @return true se l'utente esiste, false altrimenti.
     */
    public static boolean esisteUtente(String email) {
        return UserDAO.getUserByEmail(email) != null;
    }

    /**
     * Verifica se le credenziali fornite sono valide.
     *
     * @param email Email dell'utente.
     * @param password Password dell'utente.
     * @return true se le credenziali sono valide, false altrimenti.
     */
    public static boolean verificaCredenziali(String email, String password) {
        return autenticaUtente(email, password) != null;
    }

    /**
     * Verifica se l'utente con le credenziali fornite ha privilegi di amministratore.
     *
     * @param email Email dell'utente.
     * @param password Password dell'utente.
     * @return true se l'utente è un amministratore, false altrimenti o se le credenziali non sono valide.
     */
    public static boolean isAdmin(String email, String password) {
        User user = autenticaUtente(email, password);
        return user != null && user.isAdmin();
    }
}