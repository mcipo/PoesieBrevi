package database.DAO;

import database.DatabaseConnection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuoreDAO {
    private static final Logger LOGGER = Logger.getLogger(CuoreDAO.class.getName());

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