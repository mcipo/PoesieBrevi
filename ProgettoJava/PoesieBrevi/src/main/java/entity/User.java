package entity;

import DTO.ProfiloDTO;
import database.DAO.UserDAO;

import java.util.Date;
import java.util.List;

/**
 * Questa classe rappresenta un utente registrato nel sistema.
 * Contiene le informazioni dell'account utente come credenziali di accesso,
 * dati personali e il profilo pubblico associato.
 */
public class User {
    /**
     * ID univoco dell'utente nel database.
     */
    private int id;
    
    /**
     * Password dell'utente (dovrebbe essere criptata).
     */
    private final String password;
    
    /**
     * Email dell'utente, utilizzata per l'autenticazione.
     */
    private final String email;
    
    /**
     * Nome dell'utente.
     */
    private final String nome;
    
    /**
     * Cognome dell'utente.
     */
    private final String cognome;
    
    /**
     * Flag che indica se l'utente ha privilegi di amministratore.
     */
    private final boolean isAdmin;

    /**
     * Profilo pubblico associato all'utente.
     */
    private Profilo profilo;

    /**
     * Costruttore per creare un nuovo oggetto User con tutti i suoi attributi.
     *
     * @param password Password dell'utente.
     * @param email Email dell'utente per l'autenticazione.
     * @param nome Nome dell'utente.
     * @param cognome Cognome dell'utente.
     * @param isAdmin Flag che indica se l'utente è un amministratore.
     * @param profilo Profilo pubblico associato all'utente.
     */
    public User(String password, String email, String nome, String cognome, boolean isAdmin, Profilo profilo) {
        this.password = password;
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.isAdmin = isAdmin;
        this.profilo = profilo;
    }

    public Profilo getProfilo() {
        return profilo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Imposta un nuovo profilo per l'utente.
     *
     * @param profiloDTO Nuovo oggetto Profilo da associare all'utente.
     */
    public void setProfilo(ProfiloDTO profiloDTO) {
        String username = profiloDTO.getUsername();
        String bio = profiloDTO.getBio();
        String percorsoImmagine = profiloDTO.getImmagineProfilo();
        Date dataNascita = profiloDTO.getDataNascita();
        Profilo profilo = new Profilo(username, bio, percorsoImmagine, dataNascita);
        this.profilo = profilo;
    }

    public void setProfilo(Profilo profilo) {
        this.profilo = profilo;
    }

    public static User getUserFromDB(String email) {

        UserDAO userDAO = UserDAO.getUserByEmail(email);
        if (userDAO != null) {
            Profilo profilo = Profilo.getProfiloAtID(userDAO.getId());
            if (profilo == null) {
                profilo = new Profilo(userDAO.getNome() + userDAO.getCognome().charAt(0), "Nessuna biografia", "", null);
            }
            User user = new User(userDAO.getPassword(), userDAO.getEmail(), userDAO.getNome(), userDAO.getCognome(), userDAO.isAdmin(), profilo);
            user.setId(userDAO.getId());
            return user;
        }

        return null;
    }

    /**
     * Salva un nuovo utente nel database.
     * Se l'utente esiste già, non verrà salvato e il metodo restituirà false.
     *
     * @param user L'oggetto User da salvare.
     * @return true se l'utente è stato salvato con successo, false altrimenti.
     */
    public static boolean salvaUtente(User user) {
        if (user == null) {
            return false;
        }
        if (UserDAO.getUserByEmail(user.getEmail()) != null) {

            return false;
        }
        Profilo profilo = user.getProfilo();
        String email = user.getEmail();
        String password = user.getPassword();
        String nome = user.getNome();
        String cognome = user.getCognome();
        String username = profilo.getUsername();
        String bio = profilo.getBio();
        String immagineProfilo = profilo.getImmagineProfilo();
        Date dataDiNascita = profilo.getDataNascita();

        return UserDAO.addUser(email, password, nome, cognome, username, bio, immagineProfilo, dataDiNascita);
    }

    /**
     * Restituisce l'utente con più poesie.
     *
     * @return L'oggetto User che rappresenta l'utente attualmente autenticato.
     */
    public static List<int[]> getUserConPiuPoesie(){
        return UserDAO.getUserConPiuPoesie();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}