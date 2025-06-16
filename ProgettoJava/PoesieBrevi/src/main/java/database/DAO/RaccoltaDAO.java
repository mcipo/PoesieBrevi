package database.DAO;

import entity.Raccolta;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RaccoltaDAO {
    private static final Logger LOGGER = Logger.getLogger(RaccoltaDAO.class.getName());

    public static int addRaccolta(Raccolta raccolta) {
        String query = "INSERT INTO raccolte (titolo, descrizione, autore_id) VALUES (?, ?, ?)";
        try{
            int nuovoID = DatabaseConnection.executeUpdateConID(query, raccolta.getTitolo(), raccolta.getDescrizione(), raccolta.getAutoreID());
            return nuovoID;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore in addRaccolta", e);
            return -1;
        }
    }

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
