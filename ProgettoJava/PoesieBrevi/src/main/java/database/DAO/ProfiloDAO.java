package database.DAO;

import entity.Profilo;
import database.DatabaseConnection;

import java.sql.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfiloDAO {
    private static final Logger LOGGER = Logger.getLogger(ProfiloDAO.class.getName());

    public static Profilo getProfiloAtID(int userId) {
        String query = "SELECT * FROM user_profiles WHERE user_id = ?";
        try{
            ResultSet resultSet = DatabaseConnection.executeQuery(query, userId);

            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String bio = resultSet.getString("bio");
                String fotoProfiloPath = resultSet.getString("foto_profilo_path");
                Date dataNascita = resultSet.getDate("data_nascita");
                return new Profilo(username, bio, fotoProfiloPath, dataNascita);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore getting profilo", e);
        }
        return null;
    }

    public static void updateProfilo(Profilo profilo, int userId) {
        String query = "UPDATE user_profiles SET username = ?, bio = ?, foto_profilo_path = ?, data_nascita = ? WHERE user_id = ?";
        try{
            Date dataNascita = null;
            if (profilo.getDataNascita() != null) {
                dataNascita =  new java.sql.Date(profilo.getDataNascita().getTime());
            }
            DatabaseConnection.executeUpdate(query, profilo.getUsername(), profilo.getBio(), profilo.getImmagineProfilo(), dataNascita, userId);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore updating profilo", e);
        }
    }

    public static void createProfilo(Profilo profilo, int userId) {
        String query = "INSERT INTO user_profiles (user_id, username, bio, foto_profilo_path, data_nascita) VALUES (?, ?, ?, ?, ?)";
        try {
            Date dataNascita = null;
            if (profilo.getDataNascita() != null) {
                dataNascita =  new java.sql.Date(profilo.getDataNascita().getTime());
            }
            DatabaseConnection.executeUpdate(query, userId, profilo.getUsername(), profilo.getBio(), profilo.getImmagineProfilo(), dataNascita);
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                LOGGER.log(Level.SEVERE, "Errore in createProfilo - chiave duplicata", e);
                updateProfilo(profilo, userId);
            } else {
                LOGGER.log(Level.SEVERE, "Errore in createProfilo", e);
            }
        }
    }
}