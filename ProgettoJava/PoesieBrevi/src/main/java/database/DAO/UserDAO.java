package database.DAO;

import entity.User;
import entity.Profilo;
import database.DatabaseConnection;

import java.sql.*;

public class UserDAO {
    private final ProfiloDAO profiloDAO = new ProfiloDAO();

    private Connection connection;

    public UserDAO() {
        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserByEmail(String userEmail) {
        String query = "SELECT * FROM users WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userEmail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String email = resultSet.getString("email");
                    String nome = resultSet.getString("nome");
                    String cognome = resultSet.getString("cognome");
                    String password = resultSet.getString("password");
                    boolean amministratore = resultSet.getBoolean("amministratore");

                    Profilo profilo = profiloDAO.getProfiloAtID(id);
                    if (profilo == null) {
                        profilo = new Profilo(nome + cognome.charAt(0), "Nessuna biografia", "", null);
                    }
                    User user = new User(password, email, nome, cognome, amministratore, profilo);
                    user.setId(id);
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dell'utente: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean addUser(User user) {
        String query = "INSERT INTO users (email, password, nome, cognome, amministratore) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getNome());
            preparedStatement.setString(4, user.getCognome());
            preparedStatement.setBoolean(5, user.isAdmin());

            if (preparedStatement.executeUpdate() > 0) {
                String getIdQuery = "SELECT id FROM users WHERE email = ?";
                try (PreparedStatement idStatement = connection.prepareStatement(getIdQuery)) {
                    idStatement.setString(1, user.getEmail());
                    ResultSet resultSet = idStatement.executeQuery();
                    if (resultSet.next()) {
                        int userId = resultSet.getInt("id");
                        user.setId(userId);
                        profiloDAO.createProfilo(user.getProfilo(), userId);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}