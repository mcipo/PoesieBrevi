package database.DAO;

import entity.Commento;
import database.DatabaseConnection;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommentoDAO {

    private static final Logger LOGGER = Logger.getLogger(CommentoDAO.class.getName());


    public static void addCommento(Commento commento) throws SQLException {
        String query = "INSERT INTO commenti (poesia_id, autore_id, contenuto, data_creazione) VALUES (?, ?, ?, ?)";
        try{
            DatabaseConnection.executeUpdate(query, commento.getPoesiaID(), commento.getAutoreID(), commento.getTesto(), new Timestamp(commento.getDataCreazione().getTime()));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore in addCommento", e);
        }

    }

    public static List<Commento> getCommentiByPoesiaId(int poesiaId) throws SQLException {
        List<Commento> commenti = new ArrayList<>();
        String query = "SELECT * FROM commenti WHERE poesia_id = ?";
        try(ResultSet resultSet = DatabaseConnection.executeQuery(query, poesiaId);){
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int autoreID = resultSet.getInt("autore_id");
                String testo = resultSet.getString("contenuto");
                Date dataCreazione = resultSet.getTimestamp("data_creazione");
                Commento commento = new Commento(id, poesiaId, autoreID, testo, dataCreazione);
                commenti.add(commento);
            }
            return commenti;
        }catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore in getCommentiByPoesiaId", e);
        }
        return commenti;
    }
    
}