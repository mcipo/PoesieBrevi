package database.DAO;

import entity.Poesia;
import database.DatabaseConnection;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PoesiaDAO {

    private  static final Logger LOGGER = Logger.getLogger(PoesiaDAO.class.getName());

    public static List<Poesia> getUltimePoesiePerFeed(int userId, int limite) {
        List<Poesia> poesie = new ArrayList<>();
        String query = "SELECT * FROM poesie WHERE visibile = true AND autore_id != ? ORDER BY data_creazione DESC LIMIT ?";
        try{
            ResultSet resultSet = DatabaseConnection.executeQuery(query, userId, limite);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titolo = resultSet.getString("titolo");
                String contenuto = resultSet.getString("contenuto");
                boolean visibile = resultSet.getBoolean("visibile");
                Date dataCreazione = resultSet.getTimestamp("data_creazione");
                int autoreId = resultSet.getInt("autore_id");
                int raccoltaId = resultSet.getInt("raccolta_id");
                String tagString = resultSet.getString("tag");
                
                List<String> tags;
                if (tagString != null && !tagString.isEmpty()) {
                    tags = Arrays.asList(tagString.split(","));
                } else {
                    tags = new ArrayList<>();
                }

                poesie.add(new Poesia(id, titolo, contenuto, tags, visibile, dataCreazione, autoreId, raccoltaId));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"Errore in getUltimePoesiePerFeed", e);
        }

        return poesie;
    }
    

    public static List<Poesia> getPoesieByAutore(int autoreId) {
        List<Poesia> poesie = new ArrayList<>();
        String query = "SELECT * FROM poesie WHERE autore_id = ? ORDER BY data_creazione DESC";
        try{
            ResultSet resultSet = DatabaseConnection.executeQuery(query, autoreId);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titolo = resultSet.getString("titolo");
                String contenuto = resultSet.getString("contenuto");
                boolean visibile = resultSet.getBoolean("visibile");
                Date dataCreazione = resultSet.getTimestamp("data_creazione");
                int raccoltaId = resultSet.getInt("raccolta_id");
                String tagString = resultSet.getString("tag");
                
                List<String> tags;
                if (tagString != null && !tagString.isEmpty()) {
                    tags = Arrays.asList(tagString.split(","));
                } else {
                    tags = new ArrayList<>();
                }

                poesie.add(new Poesia(id, titolo, contenuto, tags, visibile, dataCreazione, autoreId, raccoltaId));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"Errore in getPoesieByAutore",e);
        }

        return poesie;
    }

    public static boolean addPoesia(Poesia poesia) {
        String query = "INSERT INTO poesie (titolo, contenuto, tag, visibile, data_creazione, autore_id, raccolta_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try{
            String tagsString = poesia.getTags() != null && !poesia.getTags().isEmpty() ? String.join(",", poesia.getTags()) : "";
            int raccoltaID = 0;
            if (poesia.getRaccoltaID() > 0){
                raccoltaID = poesia.getRaccoltaID();
            }else{
                raccoltaID = java.sql.Types.INTEGER;
            }
            int result = DatabaseConnection.executeUpdate(query, poesia.getTitolo(), poesia.getContenuto(), tagsString, poesia.getVisibile(), new Timestamp(poesia.getDataCreazione().getTime()), poesia.getAutoreID(), raccoltaID);

            return result > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"Errore in addPoesia", e);
        }

        return false;
    }

}