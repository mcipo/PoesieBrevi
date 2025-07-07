package entity;

import database.DAO.UserDAO;

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

    /**
     * Restituisce il profilo dell'utente.
     *
     * @return Oggetto Profilo associato all'utente.
     */
    public Profilo getProfilo() {
        return profilo;
    }
    
    /**
     * Restituisce l'ID dell'utente.
     *
     * @return ID dell'utente.
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'ID dell'utente.
     *
     * @param id Nuovo ID da assegnare all'utente.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce la password dell'utente.
     *
     * @return Password dell'utente.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Restituisce l'email dell'utente.
     *
     * @return Email dell'utente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Restituisce il nome dell'utente.
     *
     * @return Nome dell'utente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce il cognome dell'utente.
     *
     * @return Cognome dell'utente.
     */
    public String getCognome() {
        return cognome;
    }


    /**
     * Imposta un nuovo profilo per l'utente.
     *
     * @param profilo Nuovo oggetto Profilo da associare all'utente.
     */
    public void setProfilo(Profilo profilo) {
        this.profilo = profilo;
    }


    public static User getUserFromDB(String email) {
        return UserDAO.getUserByEmail(email);
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

    /**
     * Restituisce una rappresentazione testuale dell'oggetto User.
     *
     * @return String contenente i dettagli principali dell'utente.
     */
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