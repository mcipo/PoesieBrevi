package database.DAO;

import entity.Poesia;
import database.DatabaseConnection;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PoesiaDAO {

    private Connection connection;

    public PoesiaDAO() {
        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Poesia> getUltimePoesiePerFeed(int userId, int limite) {
        List<Poesia> poesie = new ArrayList<>();
        String query = "SELECT * FROM poesie WHERE visibile = true AND autore_id != ? ORDER BY data_creazione DESC LIMIT ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, limite);
            ResultSet resultSet = preparedStatement.executeQuery();

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
            e.printStackTrace();
        }

        return poesie;
    }
    

    public List<Poesia> getPoesieByAutore(int autoreId) {
        List<Poesia> poesie = new ArrayList<>();
        String query = "SELECT * FROM poesie WHERE autore_id = ? ORDER BY data_creazione DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, autoreId);
            ResultSet resultSet = preparedStatement.executeQuery();

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
            e.printStackTrace();
        }

        return poesie;
    }

    public boolean addPoem(Poesia poesia) {
        String query = "INSERT INTO poesie (titolo, contenuto, tag, visibile, data_creazione, autore_id, raccolta_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            String tagsString = poesia.getTags() != null && !poesia.getTags().isEmpty() ? 
                                String.join(",", poesia.getTags()) : "";
                                
            preparedStatement.setString(1, poesia.getTitolo());
            preparedStatement.setString(2, poesia.getContenuto());
            preparedStatement.setString(3, tagsString);
            preparedStatement.setBoolean(4, poesia.getVisibile());
            preparedStatement.setTimestamp(5, new Timestamp(poesia.getDataCreazione().getTime()));
            preparedStatement.setInt(6, poesia.getAutoreID());
            
            if (poesia.getRaccoltaID() > 0) {
                preparedStatement.setInt(7, poesia.getRaccoltaID());
            } else {
                preparedStatement.setNull(7, java.sql.Types.INTEGER);
            }

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}