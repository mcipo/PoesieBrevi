package database.DAO;

import database.DatabaseConnection;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object per l'entità Poesia.
 * Questa classe gestisce tutte le operazioni sul database relative alle poesie,
 * come il recupero delle poesie recenti, la ricerca delle poesie di un autore 
 * e l'aggiunta di nuove poesie.
 */
public class PoesiaDAO {

    int id;
    String titolo;
    String contenuto;
    List<String> tags;
    boolean visibile;
    Date dataCreazione;
    int autoreID;
    int raccoltaID;


    /**
     * Logger per la registrazione di eventi ed errori.
     */
    private  static final Logger LOGGER = Logger.getLogger(PoesiaDAO.class.getName());

    public PoesiaDAO(int id, String titolo, String contenuto, List<String> tags, boolean visibile, Date dataCreazione, int autoreID, int raccoltaID) {
        this.id = id;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.tags = tags;
        this.visibile = visibile;
        this.dataCreazione = dataCreazione;
        this.autoreID = autoreID;
        this.raccoltaID = raccoltaID;
    }

    public int getId() {
        return id;
    }
    public String getTitolo() {
        return titolo;
    }
    public String getContenuto() {
        return contenuto;
    }
    public List<String> getTags() {
        return tags;
    }
    public boolean getIsVisibile() {
        return visibile;
    }
    public Date getDataCreazione() {
        return dataCreazione;
    }
    public int getAutoreID() {
        return autoreID;
    }
    public int getRaccoltaID() {
        return raccoltaID;
    }

    /**
     * Recupera le poesie più recenti per il feed di un utente, escludendo le proprie poesie.
     *
     * @param userId L'ID dell'utente che visualizza il feed.
     * @param limite Il numero massimo di poesie da recuperare.
     * @return Lista di oggetti Poesia ordinate per data di creazione decrescente.
     */
    public static List<PoesiaDAO> getUltimePoesiePerFeed(int userId, int limite) {
        List<PoesiaDAO> poesie = new ArrayList<>();
        String query = "SELECT * FROM poesie WHERE visibile = true AND autore_id != ? ORDER BY data_creazione DESC LIMIT ?";
        try{
            ResultSet resultSet = DatabaseConnection.executeQuery(query, userId, limite);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titolo = resultSet.getString("titolo");
                String contenuto = resultSet.getString("contenuto");
                boolean visibile = resultSet.getBoolean("visibile");
                Date dataCreazione = resultSet.getTimestamp("data_creazione");
                int autoreId = resultSet.getInt("autore_id");
                int raccoltaId = resultSet.getInt("raccolta_id");
                String tagString = resultSet.getString("tag");
                
                List<String> tags;
                if (tagString != null && !tagString.isEmpty()) {
                    tags = Arrays.asList(tagString.split(","));
                } else {
                    tags = new ArrayList<>();
                }

                poesie.add(new PoesiaDAO(id, titolo, contenuto, tags, visibile, dataCreazione, autoreId, raccoltaId));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"Errore in getUltimePoesiePerFeed", e);
        }

        return poesie;
    }
    

    /**
     * Recupera tutte le poesie create da un determinato autore.
     *
     * @param autoreId L'ID dell'utente autore delle poesie da recuperare.
     * @return Lista di oggetti Poesia appartenenti all'autore specificato.
     */
    public static List<PoesiaDAO> getPoesieByAutore(int autoreId) {
        List<PoesiaDAO> poesie = new ArrayList<>();
        String query = "SELECT * FROM poesie WHERE autore_id = ? ORDER BY data_creazione DESC";
        try{
            ResultSet resultSet = DatabaseConnection.executeQuery(query, autoreId);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titolo = resultSet.getString("titolo");
                String contenuto = resultSet.getString("contenuto");
                boolean visibile = resultSet.getBoolean("visibile");
                Date dataCreazione = resultSet.getTimestamp("data_creazione");
                int raccoltaId = resultSet.getInt("raccolta_id");
                String tagString = resultSet.getString("tag");
                
                List<String> tags;
                if (tagString != null && !tagString.isEmpty()) {
                    tags = Arrays.asList(tagString.split(","));
                } else {
                    tags = new ArrayList<>();
                }

                poesie.add(new PoesiaDAO(id, titolo, contenuto, tags, visibile, dataCreazione, autoreId, raccoltaId));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"Errore in getPoesieByAutore",e);
        }

        return poesie;
    }

    /**
     * Aggiunge una nuova poesia al database.
     *
     * @return true se l'operazione è completata con successo, false altrimenti.
     */
    public static boolean addPoesia(String titolo, String contenuto, boolean visibile, Date dataCreazione, List<String> tags, int autoreId, int raccoltaId) {
        String query = "INSERT INTO poesie (titolo, contenuto, tag, visibile, data_creazione, autore_id, raccolta_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try{
            String tagsString = tags != null && !tags.isEmpty() ? String.join(",", tags) : "";
            if(raccoltaId <= 0){
                raccoltaId = java.sql.Types.INTEGER;
            }
            int result = DatabaseConnection.executeUpdate(query, titolo, contenuto, tagsString, visibile, new Timestamp(dataCreazione.getTime()), autoreId, raccoltaId);

            return result > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"Errore in addPoesia", e);
        }

        return false;
    }

}