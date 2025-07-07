package controller;
import entity.Profilo;
import entity.User;

public class PiattaformaController {

    private static PiattaformaController instance;
    private User currentUser;
    private PiattaformaController(){

    }

    public static PiattaformaController getInstance(){

        if (instance == null){
            instance = new PiattaformaController();
        }

        return instance;
    }

    public void setCurrentUser(User user){
        currentUser = user;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    /**
     * Autentica un utente verificando le credenziali fornite.
     *
     * @param email Email dell'utente.
     * @param password Password dell'utente.
     * @return Oggetto User se l'autenticazione ha successo, null altrimenti.
     */
    public static boolean autenticaUtente(String email, String password) {

        User user = User.getUserFromDB(email);

        if (user != null && user.getPassword().equals(password)) {
            instance.setCurrentUser(user);
            return true;
        }

        return false;
    }

    /**
     * Verifica se esiste un utente con l'email specificata nel sistema.
     *
     * @param email Email da verificare.
     * @return true se l'utente esiste, false altrimenti.
     */
    public static boolean esisteUtente(String email) {
        return User.getUserFromDB(email) != null;
    }


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
        return User.salvaUtente(user);
    }
}
