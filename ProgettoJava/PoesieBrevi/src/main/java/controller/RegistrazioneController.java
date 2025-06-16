package controller;

import entity.User;
import entity.Profilo;
import database.DAO.UserDAO;

/**
 * Controller che gestisce la logica relativa alla registrazione di nuovi utenti.
 * Fornisce funzionalità per creare nuovi utenti e verificare l'esistenza di utenti 
 * con determinate email.
 */
public class RegistrazioneController {

    /**
     * Crea un nuovo oggetto User in memoria con i dati forniti, senza salvarlo nel database.
     * Il profilo associato all'utente viene creato con valori predefiniti vuoti.
     *
     * @param nome Nome dell'utente.
     * @param cognome Cognome dell'utente.
     * @param email Email dell'utente, utilizzata per l'accesso.
     * @param password Password dell'utente.
     * @param isAdmin Flag che indica se l'utente ha privilegi di amministratore.
     * @return Oggetto User creato in memoria.
     */
    public static User creaUtenteInMemoria(String nome, String cognome, String email, String password, boolean isAdmin) {
        Profilo profilo = new Profilo("", "", "", null);

        return new User(password, email, nome, cognome, isAdmin, profilo);
    }
    
    /**
     * Salva un utente nel database dopo averlo verificato.
     * Controlla che l'utente sia valido e che non esista già un utente con la stessa email.
     *
     * @param user Oggetto User da salvare nel database.
     * @return true se il salvataggio è avvenuto con successo, false altrimenti.
     */
    public static boolean salvaUtente(User user) {
        if (user == null) {
            return false;
        }
        if (UserDAO.getUserByEmail(user.getEmail()) != null) {
            return false;
        }
        
        return UserDAO.addUser(user);
    }

    /**
     * Verifica se esiste già un utente con l'email specificata.
     *
     * @param email Email da verificare.
     * @return true se esiste già un utente con questa email, false altrimenti.
     */
    public static boolean esisteUtente(String email) {
        return UserDAO.getUserByEmail(email) != null;
    }
}