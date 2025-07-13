package database.DAO;


import controller.PiattaformaController;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object per l'entità User.
 * Questa classe gestisce tutte le operazioni sul database relative agli utenti,
 * come la ricerca di utenti per email e l'aggiunta di nuovi utenti.
 */
public class UserDAO {
    private static PiattaformaController piattaformaController = PiattaformaController.getInstance();
    /**
     * Logger per la registrazione di eventi ed errori.
     */
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    private int id;
    private final String password;
    private final String email;
    private final String nome;
    private final String cognome;
    private final boolean isAdmin;
    public UserDAO(String password, String email, String nome, String cognome, boolean isAdmin) {
        this.password = password;
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.isAdmin = isAdmin;
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
     * Recupera un utente dal database tramite la sua email.
     *
     * @param userEmail L'email dell'utente da cercare.
     * @return L'oggetto User corrispondente all'email specificata, o null se non trovato.
     */
    public static UserDAO getUserByEmail(String userEmail) {
        String query = "SELECT * FROM users WHERE email = ?";
        try{
            ResultSet resultSet = DatabaseConnection.executeQuery(query, userEmail);
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String nome = resultSet.getString("nome");
                String cognome = resultSet.getString("cognome");
                String password = resultSet.getString("password");
                boolean amministratore = resultSet.getBoolean("amministratore");

                UserDAO user = new UserDAO(password, email, nome, cognome, amministratore);
                user.setId(id);
                return user;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore in getUserByEmail", e);
        }
        return null;
    }

    /**
     * Aggiunge un nuovo utente al database e crea il profilo associato.
     *
     * @return true se l'operazione è completata con successo, false altrimenti.
     */
    public static boolean addUser(String email, String password, String nome, String cognome, String username, String bio, String immagineProfilo, Date dataDiNascita) {
        String query = "INSERT INTO users (email, password, nome, cognome, amministratore) VALUES (?, ?, ?, ?, ?)";
        try{
            int result = DatabaseConnection.executeUpdate(query,email, password, nome, cognome, false);
            if (result > 0) {
                String queryID = "SELECT id FROM users WHERE email = ?";
                ResultSet resultSet = DatabaseConnection.executeQuery(queryID, email);
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    piattaformaController.getCurrentUser().setId(userId);
                    ProfiloDAO.createProfilo(username, bio, immagineProfilo, dataDiNascita, userId);
                    return true;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore in addUser", e);
        }
        return false;
    }

    public static List<int[]> getUserConPiuPoesie() {
        String query = "SELECT autore_id, COUNT(*) AS numero_poesie FROM poesie GROUP BY autore_id ORDER BY numero_poesie DESC LIMIT 10";
        try{
            ResultSet resultSet = DatabaseConnection.executeQuery(query);
            List<int[]> datiAutori = new ArrayList<>();
            while (resultSet.next()) {
                int autoreId = resultSet.getInt("autore_id");
                int numeroPoesie = resultSet.getInt("numero_poesie");
                datiAutori.add(new int[]{autoreId, numeroPoesie});
            }
            return datiAutori;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}