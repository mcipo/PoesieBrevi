package controller;
import DTO.PoesiaDTO;
import entity.Poesia;
import entity.Profilo;
import entity.User;

import java.util.List;

/**
 * Controller principale della piattaforma per la gestione delle poesie.
 * Implementa il pattern Singleton per garantire una singola istanza del controller
 * e gestisce le operazioni relative agli utenti, all'autenticazione e al recupero
 * delle poesie.
 * 
 */
public class PiattaformaController {

    /**
     * Istanza singleton del controller.
     */
    private static PiattaformaController instance;

    /**
     * Utente attualmente autenticato nella piattaforma.
     */
    private User currentUser;

    /**
     * Costruttore privato per implementare il pattern Singleton.
     * Previene l'istanziazione diretta della classe dall'esterno.
     */
    private PiattaformaController(){

    }

    /**
     * Restituisce l'istanza singleton del PiattaformaController.
     * Se l'istanza non esiste ancora, la crea.
     * 
     * @return L'unica istanza di PiattaformaController.
     */
    public static PiattaformaController getInstance(){

        if (instance == null){
            instance = new PiattaformaController();
        }

        return instance;
    }

    /**
     * Imposta l'utente attualmente autenticato nella piattaforma.
     * 
     * @param user L'utente da impostare come utente corrente.
     */
    public void setCurrentUser(User user){
        currentUser = user;
    }

    /**
     * Restituisce l'utente attualmente autenticato nella piattaforma.
     * 
     * @return L'utente corrente, o null se nessun utente è autenticato.
     */
    public User getCurrentUser(){
        return currentUser;
    }

    /**
     * Autentica un utente verificando le credenziali fornite.
     * Se l'autenticazione ha successo, imposta l'utente come utente corrente.
     *
     * @param email Email dell'utente da autenticare.
     * @param password Password dell'utente da verificare.
     * @return true se l'autenticazione ha successo, false altrimenti.
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
     * Delega il salvataggio al metodo statico della classe User.
     *
     * @param user Oggetto User da salvare nel database.
     * @return true se il salvataggio è avvenuto con successo, false altrimenti.
     */
    public static boolean salvaUtente(User user) {
        return User.salvaUtente(user);
    }

    /**
     * Recupera le poesie pubblicate nell'ultima settimana e le restituisce come DTO.
     * Converte la lista di oggetti Poesia in una lista di PoesiaDTO per
     * ottimizzare il trasferimento dei dati.
     * 
     * @return Lista di PoesiaDTO contenente le poesie dell'ultima settimana.
     */
    public static List<PoesiaDTO> getPoesieUltimaSettimana(){

        List<Poesia> poesie = Poesia.getPoesieInIntervallo(7);
        return poesie.stream()
                .map(p -> new PoesiaDTO(p.getId(), p.getTitolo(), p.getAutoreID()))
                .toList();


    }

    /**
     * Recupera gli utenti che hanno pubblicato il maggior numero di poesie.
     * Restituisce una lista di array di interi contenenti le informazioni
     * sugli utenti più prolifici della piattaforma.
     * 
     * @return Lista di array di interi rappresentanti gli utenti con più poesie.
     *         Ogni array contiene le informazioni statistiche dell'utente.
     */
    public static List<int[]> getUserConPiuPoesie(){
        return User.getUserConPiuPoesie();
    }

}
