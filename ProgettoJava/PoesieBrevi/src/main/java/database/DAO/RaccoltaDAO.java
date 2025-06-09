package database.DAO;

import entity.Raccolta;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RaccoltaDAO {

    private Connection connection;

    public RaccoltaDAO() {
        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int addRaccolta(Raccolta raccolta) {
        String query = "INSERT INTO raccolte (titolo, descrizione, autore_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, raccolta.getTitolo());
            statement.setString(2, raccolta.getDescrizione());
            statement.setInt(3, raccolta.getAutoreID());
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return -1;
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Raccolta> getRaccoltaPerAutore(int autoreId) {
        List<Raccolta> raccoltaList = new ArrayList<>();
        String query = "SELECT * FROM raccolte WHERE autore_id = ? ORDER BY id DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, autoreId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titolo = resultSet.getString("titolo");
                String descrizione = resultSet.getString("descrizione");
                int autoreID = resultSet.getInt("autore_id");

                raccoltaList.add(new Raccolta(id, titolo, descrizione, autoreID));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return raccoltaList;
    }

}
