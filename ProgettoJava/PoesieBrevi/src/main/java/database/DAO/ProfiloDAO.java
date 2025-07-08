package database.DAO;

import entity.Profilo;
import database.DatabaseConnection;

import java.sql.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object per l'entità Profilo.
 * Questa classe gestisce tutte le operazioni sul database relative ai profili degli utenti,
 * come il recupero, l'aggiornamento e la creazione di profili.
 */
public class ProfiloDAO {
    /**
     * Logger per la registrazione di eventi ed errori.
     */
    private static final Logger LOGGER = Logger.getLogger(ProfiloDAO.class.getName());

    private ProfiloDAO(){

    }

    /**
     * Recupera il profilo di un utente dato il suo ID.
     *
     * @param userId L'ID dell'utente di cui recuperare il profilo.
     * @return L'oggetto Profilo associato all'utente, o null se non esiste.
     */
    public static Profilo getProfiloAtID(int userId) {
        String query = "SELECT * FROM user_profiles WHERE user_id = ?";
        try{
            ResultSet resultSet = DatabaseConnection.executeQuery(query, userId);

            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String bio = resultSet.getString("bio");
                String fotoProfiloPath = resultSet.getString("foto_profilo_path");
                Date dataNascita = resultSet.getDate("data_nascita");
                return new Profilo(username, bio, fotoProfiloPath, dataNascita);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore getting profilo", e);
        }
        return null;
    }

    /**
     * Aggiorna un profilo esistente nel database.
     *
     * @param userId L'ID dell'utente proprietario del profilo.
     */
    public static void updateProfilo(String username, String bio, String immagineProfilo, Date dataDiNascita, int userId) {
        String query = "UPDATE user_profiles SET username = ?, bio = ?, foto_profilo_path = ?, data_nascita = ? WHERE user_id = ?";
        try{
            if (dataDiNascita != null) {
                dataDiNascita =  new java.sql.Date(dataDiNascita.getTime());
            }
            DatabaseConnection.executeUpdate(query, username, bio, immagineProfilo, dataDiNascita, userId);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore updating profilo", e);
        }
    }

    /**
     * Crea un nuovo profilo nel database.
     * Se viene rilevato un profilo esistente con lo stesso ID utente,
     * chiama il metodo updateProfilo per aggiornarlo invece.
     *
     * @param userId L'ID dell'utente proprietario del profilo.
     */
    public static void createProfilo(String username, String bio, String immagineProfilo, Date dataDiNascita, int userId) {
        String query = "INSERT INTO user_profiles (user_id, username, bio, foto_profilo_path, data_nascita) VALUES (?, ?, ?, ?, ?)";
        try {
            if (dataDiNascita != null) {
                dataDiNascita =  new java.sql.Date(dataDiNascita.getTime());
            }
            DatabaseConnection.executeUpdate(query, userId, username, bio, immagineProfilo, dataDiNascita);
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                LOGGER.log(Level.SEVERE, "Errore in createProfilo - chiave duplicata", e);
                updateProfilo(username, bio, immagineProfilo, dataDiNascita, userId);
            } else {
                LOGGER.log(Level.SEVERE, "Errore in createProfilo", e);
            }
        }
    }
}