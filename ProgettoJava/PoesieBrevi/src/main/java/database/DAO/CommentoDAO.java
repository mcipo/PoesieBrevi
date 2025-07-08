package database.DAO;

import database.DatabaseConnection;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object per l'entità Commento.
 * Questa classe gestisce tutte le operazioni sul database relative ai commenti,
 * come l'aggiunta di nuovi commenti e il recupero dei commenti di una poesia.
 */
public class CommentoDAO {

    private int id;
    private int poesiaId;
    private int autoreId;
    private String testo;
    private Date dataCreazione;


    /**
     * Logger per la registrazione di eventi ed errori.
     */
    private static final Logger LOGGER = Logger.getLogger(CommentoDAO.class.getName());

    private CommentoDAO(int id, int poesiaId, int autoreId, String testo, Date dataCreazione) {
        this.id = id;
        this.poesiaId = poesiaId;
        this.autoreId = autoreId;
        this.testo = testo;
        this.dataCreazione = dataCreazione;
    }

    public int getId() {
        return id;
    }
    public int getPoesiaId() {
        return poesiaId;
    }
    public int getAutoreId() {
        return autoreId;
    }
    public String getTesto() {
        return testo;
    }
    public Date getDataCreazione() {
        return dataCreazione;
    }


    /**
     * Aggiunge un nuovo commento al database.
     *
     *
     * @throws SQLException Se si verifica un errore durante l'operazione di inserimento.
     */
    public static void addCommento(int poesiaId, int autoreId, String testo, Date dataCreazione) throws SQLException {
        String query = "INSERT INTO commenti (poesia_id, autore_id, contenuto, data_creazione) VALUES (?, ?, ?, ?)";
        try{
            System.out.println(poesiaId + " " + autoreId + " " + testo + " " + dataCreazione);
            DatabaseConnection.executeUpdate(query, poesiaId, autoreId, testo, new Timestamp(dataCreazione.getTime()));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore in addCommento", e);
        }
    }

    /**
     * Recupera tutti i commenti associati a una determinata poesia.
     *
     * @param poesiaId L'ID della poesia di cui recuperare i commenti.
     * @return Lista di oggetti Commento associati alla poesia specificata.
     * @throws SQLException Se si verifica un errore durante l'operazione di recupero.
     */
    public static List<CommentoDAO> getCommentiByPoesiaId(int poesiaId) {
        List<CommentoDAO> commenti = new ArrayList<>();
        String query = "SELECT * FROM commenti WHERE poesia_id = ?";
        try(ResultSet resultSet = DatabaseConnection.executeQuery(query, poesiaId);){
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int autoreID = resultSet.getInt("autore_id");
                String testo = resultSet.getString("contenuto");
                Date dataCreazione = resultSet.getTimestamp("data_creazione");
                CommentoDAO commento = new CommentoDAO(id, poesiaId, autoreID, testo, dataCreazione);
                commenti.add(commento);
            }
            return commenti;
        }catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore in getCommentiByPoesiaId", e);
        }
        return commenti;
    }
}
