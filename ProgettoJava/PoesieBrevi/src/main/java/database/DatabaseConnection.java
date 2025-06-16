package database;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/poesie_brevi_db";
    private static final String USER = "claudia";
    //Inserire la password per l'utente del Database
    private static final String PASSWORD = "passwordSicura";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            return connection;
        } catch (ClassNotFoundException ex) {
            throw new SQLException("MySQL JDBC Driver not found", ex);
        }
    }

    public static ResultSet executeQuery(String query, Object... params) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
        return statement.executeQuery();
    }

    public static int executeUpdate(String query, Object... params) throws SQLException {
        int result = -1;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            result = statement.executeUpdate();
        }
        return result;
    }

    public static int executeUpdateConID(String query, Object... params) throws SQLException {
        int nuovoID = -1;
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }

        int affectedRows = statement.executeUpdate();
        if (affectedRows > 0) {
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    nuovoID = generatedKeys.getInt(1); // Ottieni l'ID
                }
            }
        }
        return nuovoID;
    }
}
