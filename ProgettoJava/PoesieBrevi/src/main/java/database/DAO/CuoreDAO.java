package database.DAO;

import database.DatabaseConnection;

import java.sql.*;

public class CuoreDAO {

    private Connection connection;

    public CuoreDAO() {
        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean addCuore(int poesiaId, int userId) {
        String sql = "INSERT INTO cuori (poesia_id, utente_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, poesiaId);
            statement.setInt(2, userId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean removeCuore(int poesiaId, int userId) {
        String sql = "DELETE FROM cuori WHERE poesia_id = ? AND utente_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, poesiaId);
            statement.setInt(2, userId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getNumCuori(int poesiaId) {
        String sql = "SELECT COUNT(*) AS numeroCuori FROM cuori WHERE poesia_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, poesiaId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("numeroCuori");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    

    public boolean hasUserLiked(int poesiaId, int userId) {
        String sql = "SELECT COUNT(*) AS count FROM cuori WHERE poesia_id = ? AND utente_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, poesiaId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count") > 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}