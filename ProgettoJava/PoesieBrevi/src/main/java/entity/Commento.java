package entity;

import database.DAO.CommentoDAO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Questa classe rappresenta un commento lasciato da un utente su una poesia.
 * Permette l'interazione tra utenti attraverso feedback e discussioni sulle poesie pubblicate.
 */
public class Commento {
    /**
     * ID univoco del commento nel database.
     */
    private int id;
    
    /**
     * ID della poesia a cui si riferisce il commento.
     */
    private final int poesiaID;
    
    /**
     * ID dell'utente che ha scritto il commento.
     */
    private final int autoreID;
    
    /**
     * Testo del commento.
     */
    private final String testo;
    
    /**
     * Data in cui è stato creato il commento.
     */
    private final Date dataCreazione;

    /**
     * Costruttore per creare un nuovo oggetto Commento.
     *
     * @param id ID univoco del commento.
     * @param poesiaID ID della poesia commentata.
     * @param autoreID ID dell'autore del commento.
     * @param testo Contenuto testuale del commento.
     * @param dataCreazione Data di creazione del commento.
     */
    public Commento(int id, int poesiaID, int autoreID, String testo, Date dataCreazione) {
        this.id = id;
        this.poesiaID = poesiaID;
        this.autoreID = autoreID;
        this.testo = testo;
        this.dataCreazione = dataCreazione;
    }

    /**
     * Restituisce l'ID del commento.
     *
     * @return ID del commento.
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'ID del commento.
     *
     * @param id Nuovo ID da assegnare al commento.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce l'ID della poesia a cui si riferisce il commento.
     *
     * @return ID della poesia commentata.
     */
    public int getPoesiaID() {
        return poesiaID;
    }

    /**
     * Restituisce l'ID dell'autore del commento.
     *
     * @return ID dell'autore del commento.
     */
    public int getAutoreID() {
        return autoreID;
    }

    /**
     * Restituisce il testo del commento.
     *
     * @return Contenuto testuale del commento.
     */
    public String getTesto() {
        return testo;
    }

    /**
     * Restituisce la data di creazione del commento.
     *
     * @return Data di creazione del commento.
     */
    public Date getDataCreazione() {
        return dataCreazione;
    }

    public static List<Commento> getCommentiByPoesiaId(int poesiaId) throws SQLException {
        List<Commento> commenti = CommentoDAO.getCommentiByPoesiaId(poesiaId);
        return commenti;
    }

    public static boolean salvaCommento(Commento nuovoCommento) {
        try {
            CommentoDAO.addCommento(nuovoCommento);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Restituisce una rappresentazione testuale dell'oggetto Commento.
     *
     * @return String contenente tutti i dettagli del commento.
     */
    @Override
    public String toString() {
        return "Commento{" +
                "id=" + id +
                ", poesiaID=" + poesiaID +
                ", autoreID=" + autoreID +
                ", testo='" + testo + '\'' +
                ", dataCreazione=" + dataCreazione +
                '}';
    }
}