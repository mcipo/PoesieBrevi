package database;

import java.sql.*;

/**
 * Classe che gestisce la connessione al database e fornisce metodi per eseguire query SQL.
 * Implementa un modello di connessione singleton semplificato per tutte le operazioni
 * di accesso al database nell'applicazione.
 */
public class DatabaseConnection {
    /**
     * URL di connessione al database MySQL.
     */
    private static final String URL = "jdbc:mysql://localhost:3306/poesie_brevi_db";

    /**
     * Nome utente per la connessione al database.
     */
    private static final String USER = "root";
    
    /**
     * Password per la connessione al database.
     */
    private static final String PASSWORD = "password";

    private DatabaseConnection(){}

    /**
     * Stabilisce e restituisce una connessione al database.
     *
     * @return Oggetto Connection per interagire con il database.
     * @throws SQLException Se si verifica un errore durante la connessione.
     */
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

    /**
     * Esegue una query SQL che restituisce un risultato (SELECT).
     *
     * @param query La query SQL da eseguire.
     * @param params I parametri da inserire nella query preparata.
     * @return ResultSet contenente i risultati della query.
     * @throws SQLException Se si verifica un errore durante l'esecuzione della query.
     */
    public static ResultSet executeQuery(String query, Object... params) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
        return statement.executeQuery();
    }

    /**
     * Esegue una query SQL che modifica il database (INSERT, UPDATE, DELETE).
     *
     * @param query La query SQL da eseguire.
     * @param params I parametri da inserire nella query preparata.
     * @return Il numero di righe interessate dall'operazione, o -1 in caso di errore.
     * @throws SQLException Se si verifica un errore durante l'esecuzione della query.
     */
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

    /**
     * Esegue una query SQL che modifica il database e restituisce l'ID generato.
     * Utile per le operazioni di INSERT che generano una chiave primaria auto-incrementata.
     *
     * @param query La query SQL da eseguire.
     * @param params I parametri da inserire nella query preparata.
     * @return L'ID generato dall'operazione, o -1 in caso di errore.
     * @throws SQLException Se si verifica un errore durante l'esecuzione della query.
     */
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
