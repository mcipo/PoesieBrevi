package database.DAO;

import database.DatabaseConnection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object per la gestione dei "cuori" (mi piace) alle poesie.
 * Questa classe gestisce tutte le operazioni sul database relative ai cuori,
 * come l'aggiunta, la rimozione e il conteggio dei cuori su una poesia.
 */
public class CuoreDAO {
    /**
     * Logger per la registrazione di eventi ed errori.
     */
    private static final Logger LOGGER = Logger.getLogger(CuoreDAO.class.getName());

    private CuoreDAO() {

    }

    /**
     * Aggiunge un nuovo "cuore" (mi piace) a una poesia da parte di un utente.
     *
     * @param poesiaId L'ID della poesia a cui aggiungere il cuore.
     * @param userId L'ID dell'utente che mette il cuore.
     * @return true se l'operazione è completata con successo, false altrimenti.
     */
    public static boolean addCuore(int poesiaId, int userId) {
        String query = "INSERT INTO cuori (poesia_id, utente_id) VALUES (?, ?)";
        try{
            int result = DatabaseConnection.executeUpdate(query, poesiaId, userId);
            return result > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore in addCuore", e);
            return false;
        }
    }

    /**
     * Rimuove un "cuore" (mi piace) da una poesia per un determinato utente.
     *
     * @param poesiaId L'ID della poesia da cui rimuovere il cuore.
     * @param userId L'ID dell'utente che rimuove il cuore.
     * @return true se l'operazione è completata con successo, false altrimenti.
     */
    public static boolean removeCuore(int poesiaId, int userId) {
        String query = "DELETE FROM cuori WHERE poesia_id = ? AND utente_id = ?";
        try{
            int result = DatabaseConnection.executeUpdate(query, poesiaId, userId);
            return result > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore in removeCuore", e);
            return false;
        }
    }

    /**
     * Conta il numero di "cuori" (mi piace) ricevuti da una poesia.
     *
     * @param poesiaId L'ID della poesia di cui contare i cuori.
     * @return Il numero di cuori ricevuti dalla poesia, o -1 in caso di errore.
     */
    public static int getNumCuori(int poesiaId) {
        String query = "SELECT COUNT(*) AS numeroCuori FROM cuori WHERE poesia_id = ?";
        try{
            ResultSet resultSet = DatabaseConnection.executeQuery(query, poesiaId);
            if (resultSet.next()) {
                return resultSet.getInt("numeroCuori");
            }
            return 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore getNumCuori", e);
            return -1;
        }
    }
    
    /**
     * Verifica se un utente ha messo un "cuore" (mi piace) a una specifica poesia.
     *
     * @param poesiaId L'ID della poesia da verificare.
     * @param userId L'ID dell'utente da verificare.
     * @return true se l'utente ha messo un cuore alla poesia, false altrimenti.
     */
    public static boolean hasUserLiked(int poesiaId, int userId) {
        String query = "SELECT COUNT(*) AS count FROM cuori WHERE poesia_id = ? AND utente_id = ?";
        try{
            ResultSet resultSet = DatabaseConnection.executeQuery(query, poesiaId, userId);
            if (resultSet.next()) {
                return resultSet.getInt("count") > 0;
            }
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore getNumCuori", e);
            return false;
        }
    }
}