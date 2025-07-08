package database.DAO;
/**
 * Aggiustare getRaccoltaPerAutore...
 */


import entity.Raccolta;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object per l'entità Raccolta.
 * Questa classe gestisce tutte le operazioni sul database relative alle raccolte di poesie,
 * come la creazione di nuove raccolte e il recupero delle raccolte di un autore.
 */
public class RaccoltaDAO {
    /**
     * Logger per la registrazione di eventi ed errori.
     */
    private static final Logger LOGGER = Logger.getLogger(RaccoltaDAO.class.getName());

    private RaccoltaDAO() {

    }

    /**
     * Aggiunge una nuova raccolta al database.
     *
     * @return L'ID della raccolta appena creata, o -1 in caso di errore.
     */
    public static int addRaccolta(String titolo, String descrizione, int autoreId) {
        String query = "INSERT INTO raccolte (titolo, descrizione, autore_id) VALUES (?, ?, ?)";
        try{
            int nuovoID = DatabaseConnection.executeUpdateConID(query, titolo, descrizione, autoreId);
            return nuovoID;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore in addRaccolta", e);
            return -1;
        }
    }

    /**
     * Recupera tutte le raccolte create da un determinato autore.
     *
     * @param autoreId L'ID dell'utente autore delle raccolte da recuperare.
     * @return Lista di oggetti Raccolta appartenenti all'autore specificato.
     */
    public static List<Raccolta> getRaccoltaPerAutore(int autoreId) {
        List<Raccolta> raccoltaList = new ArrayList<>();
        String query = "SELECT * FROM raccolte WHERE autore_id = ? ORDER BY id DESC";
        try{
            ResultSet resultSet = DatabaseConnection.executeQuery(query, autoreId);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titolo = resultSet.getString("titolo");
                String descrizione = resultSet.getString("descrizione");
                int autoreID = resultSet.getInt("autore_id");

                raccoltaList.add(new Raccolta(id, titolo, descrizione, autoreID));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore in getRaccoltaPerAutore", e);
        }

        return raccoltaList;
    }

}
