package database.DAO;

import database.DatabaseConnection;
import entity.User;
import entity.Profilo;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    public static User getUserByEmail(String userEmail) {
        String query = "SELECT * FROM users WHERE email = ?";
        try{
            ResultSet resultSet = DatabaseConnection.executeQuery(query, userEmail);
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String nome = resultSet.getString("nome");
                String cognome = resultSet.getString("cognome");
                String password = resultSet.getString("password");
                boolean amministratore = resultSet.getBoolean("amministratore");

                Profilo profilo = ProfiloDAO.getProfiloAtID(id);
                if (profilo == null) {
                    profilo = new Profilo(nome + cognome.charAt(0), "Nessuna biografia", "", null);
                }
                User user = new User(password, email, nome, cognome, amministratore, profilo);
                user.setId(id);
                return user;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore in getUserByEmail", e);
        }
        return null;
    }

    public static boolean addUser(User user) {
        String query = "INSERT INTO users (email, password, nome, cognome, amministratore) VALUES (?, ?, ?, ?, ?)";
        try{
            int result = DatabaseConnection.executeUpdate(query,user.getEmail(), user.getPassword(), user.getNome(), user.getCognome(), user.isAdmin());
            if (result > 0) {
                String queryID = "SELECT id FROM users WHERE email = ?";
                ResultSet resultSet = DatabaseConnection.executeQuery(queryID, user.getEmail());
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    user.setId(userId);
                    ProfiloDAO.createProfilo(user.getProfilo(), userId);
                    return true;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore in addUser", e);
        }
        return false;
    }

}