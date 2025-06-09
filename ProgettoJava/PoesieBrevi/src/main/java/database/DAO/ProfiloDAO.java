package database.DAO;

import entity.Profilo;
import database.DatabaseConnection;

import java.sql.*;
import java.util.Date;

public class ProfiloDAO {
    private Connection connection;

    public ProfiloDAO() {
        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Profilo getProfiloAtID(int userId) {
        String query = "SELECT * FROM user_profiles WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String bio = resultSet.getString("bio");
                String fotoProfiloPath = resultSet.getString("foto_profilo_path");
                Date dataNascita = resultSet.getDate("data_nascita");
                return new Profilo(username, bio, fotoProfiloPath, dataNascita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateProfilo(Profilo profilo, int userId) {
        String query = "UPDATE user_profiles SET username = ?, bio = ?, foto_profilo_path = ?, data_nascita = ? WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, profilo.getUsername());
            preparedStatement.setString(2, profilo.getBio());
            preparedStatement.setString(3, profilo.getImmagineProfilo());
            if (profilo.getDataNascita() != null) {
                preparedStatement.setDate(4, new java.sql.Date(profilo.getDataNascita().getTime()));
            } else {
                preparedStatement.setDate(4, null);
            }
            preparedStatement.setInt(5, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createProfilo(Profilo profilo, int userId) {
        String query = "INSERT INTO user_profiles (user_id, username, bio, foto_profilo_path, data_nascita) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            System.out.println(profilo.getUsername());
            preparedStatement.setString(2, profilo.getUsername());
            preparedStatement.setString(3, profilo.getBio());
            preparedStatement.setString(4, profilo.getImmagineProfilo());
            if (profilo.getDataNascita() != null) {
                preparedStatement.setDate(5, new java.sql.Date(profilo.getDataNascita().getTime()));
            } else {
                preparedStatement.setDate(5, null);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Chiave duplicata rilevata, tentativo di aggiornamento del profilo esistente");
                updateProfilo(profilo, userId);
            } else {
                e.printStackTrace();
            }
        }
    }
}