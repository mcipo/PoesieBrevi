package database.DAO;

import entity.Commento;
import database.DatabaseConnection;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CommentoDAO {

    private Connection connection;

    public CommentoDAO() {
        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCommento(Commento commento) throws SQLException {
        String sql = "INSERT INTO commenti (poesia_id, autore_id, contenuto, data_creazione) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, commento.getPoesiaID());
            statement.setInt(2, commento.getAutoreID());
            statement.setString(3, commento.getTesto());
            statement.setTimestamp(4, new Timestamp(commento.getDataCreazione().getTime()));
            statement.executeUpdate();
        }
    }

    public List<Commento> getCommentiByPoesiaId(int poesiaId) throws SQLException {
        List<Commento> commenti = new ArrayList<>();
        String sql = "SELECT * FROM commenti WHERE poesia_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, poesiaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int autoreID = resultSet.getInt("autore_id");
                String testo = resultSet.getString("contenuto");
                Date dataCreazione = resultSet.getTimestamp("data_creazione");
                Commento commento = new Commento(id, poesiaId, autoreID, testo, dataCreazione);
                commenti.add(commento);
                
            }
            return commenti;
        }
    }
    
}