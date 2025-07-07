package entity;

import database.DAO.PoesiaDAO;

import java.util.Date;
import java.util.List;

/**
 * Questa classe rappresenta l'entità Poesia nel sistema.
 * Contiene tutte le informazioni relative a una poesia pubblicata da un utente,
 * inclusi i tag, la visibilità e l'eventuale appartenenza a una raccolta.
 */
public class Poesia {
    /**
     * ID univoco della poesia nel database.
     */
    private int id;
    
    /**
     * Titolo della poesia.
     */
    private String titolo;
    
    /**
     * Contenuto testuale della poesia.
     */
    private final String contenuto;
    
    /**
     * Lista di tag associati alla poesia per facilitarne la ricerca.
     */
    private final List<String> tags;
    
    /**
     * Flag che indica se la poesia è visibile pubblicamente o privata.
     */
    private final boolean visibile;
    
    /**
     * Data di creazione della poesia.
     */
    private final Date dataCreazione;
    
    /**
     * ID dell'utente autore della poesia.
     */
    private final int autoreID;
    
    /**
     * ID della raccolta a cui appartiene la poesia, se presente.
     * Un valore -1 indica che non appartiene a nessuna raccolta.
     */
    private final int raccoltaID;

    /**
     * Costruttore per creare un nuovo oggetto Poesia con tutti i suoi attributi.
     *
     * @param id ID della poesia.
     * @param titolo Titolo della poesia.
     * @param contenuto Testo della poesia.
     * @param tags Lista di tag associati alla poesia.
     * @param visibile Indica se la poesia è visibile pubblicamente.
     * @param dataCreazione Data di creazione della poesia.
     * @param autoreID ID dell'autore della poesia.
     * @param raccoltaID ID della raccolta a cui appartiene (se presente).
     */
    public Poesia(int id, String titolo, String contenuto, List<String> tags, boolean visibile, Date dataCreazione, int autoreID, int raccoltaID) {
        this.id = id;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.tags = tags;
        this.visibile = visibile;
        this.dataCreazione = dataCreazione;
        this.autoreID = autoreID;
        this.raccoltaID = raccoltaID;
    }

    /**
     * Restituisce l'ID univoco della poesia.
     *
     * @return ID della poesia.
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'ID della poesia.
     *
     * @param id Nuovo ID da assegnare alla poesia.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce il titolo della poesia.
     *
     * @return Titolo della poesia.
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Imposta un nuovo titolo per la poesia.
     *
     * @param titolo Nuovo titolo da assegnare alla poesia.
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Restituisce il contenuto testuale della poesia.
     *
     * @return Il testo della poesia.
     */
    public String getContenuto() {
        return contenuto;
    }

    /**
     * Restituisce la lista di tag associati alla poesia.
     *
     * @return Lista di tag della poesia.
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Restituisce lo stato di visibilità della poesia.
     *
     * @return true se la poesia è visibile pubblicamente, false se è privata.
     */
    public boolean getVisibile() {
        return visibile;
    }

    /**
     * Restituisce la data di creazione della poesia.
     *
     * @return Data di creazione della poesia.
     */
    public Date getDataCreazione() {
        return dataCreazione;
    }

    /**
     * Restituisce l'ID dell'utente autore della poesia.
     *
     * @return ID dell'autore della poesia.
     */
    public int getAutoreID() {
        return autoreID;
    }

    /**
     * Restituisce l'ID della raccolta a cui appartiene la poesia.
     *
     * @return ID della raccolta, o -1 se la poesia non appartiene a nessuna raccolta.
     */
    public int getRaccoltaID() {
        return raccoltaID;
    }

    public static List<Poesia> getPoesieByAutore(int autoreId) {
        return PoesiaDAO.getPoesieByAutore(autoreId);
    }

    public static List<Poesia> getUltimePoesiePerFeed(int userId, int limit) {
        return PoesiaDAO.getUltimePoesiePerFeed(userId, limit);
    }

    public boolean salvaPoesia() {
        return PoesiaDAO.addPoesia(this);
    }

    /**
     * Restituisce una rappresentazione testuale dell'oggetto Poesia.
     *
     * @return String contenente tutti i dettagli della poesia.
     */
    @Override
    public String toString() {
        return "Poesia{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", contenuto='" + contenuto + '\'' +
                ", tags=" + tags +
                ", visibile=" + visibile +
                ", dataCreazione=" + dataCreazione +
                ", autoreID=" + autoreID +
                ", raccoltaID=" + raccoltaID +
                '}';
    }
}

