package entity;

import database.DAO.RaccoltaDAO;

import java.util.List;

/**
 * Questa classe rappresenta una raccolta di poesie nel sistema.
 * Una raccolta consente agli utenti di raggruppare diverse poesie sotto un tema comune
 * o per qualsiasi altro criterio di organizzazione personale.
 */
public class Raccolta {
    /**
     * ID univoco della raccolta nel database.
     */
    private int id;
    
    /**
     * Titolo della raccolta.
     */
    private String titolo;
    
    /**
     * Descrizione che spiega il contenuto o il tema della raccolta.
     */
    private final String descrizione;
    
    /**
     * ID dell'utente che ha creato questa raccolta.
     */
    private final int autoreID;

    /**
     * Costruttore per creare un nuovo oggetto Raccolta.
     *
     * @param id ID univoco della raccolta.
     * @param titolo Titolo della raccolta.
     * @param descrizione Descrizione o tema della raccolta.
     * @param autoreID ID dell'autore che ha creato la raccolta.
     */
    public Raccolta(int id, String titolo, String descrizione, int autoreID) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.autoreID = autoreID;
    }

    /**
     * Restituisce l'ID univoco della raccolta.
     *
     * @return ID della raccolta.
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'ID della raccolta.
     *
     * @param id Nuovo ID da assegnare alla raccolta.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce il titolo della raccolta.
     *
     * @return Titolo della raccolta.
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Imposta un nuovo titolo per la raccolta.
     *
     * @param titolo Nuovo titolo da assegnare alla raccolta.
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Restituisce la descrizione della raccolta.
     *
     * @return Descrizione della raccolta.
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Restituisce l'ID dell'autore della raccolta.
     *
     * @return ID dell'autore che ha creato la raccolta.
     */
    public int getAutoreID() {
        return autoreID;
    }

    public int salvaRaccolta() {
        return RaccoltaDAO.addRaccolta(this);
    }

    public static List<Raccolta> getRaccoltaByAutore(int autoreId) {
        return RaccoltaDAO.getRaccoltaPerAutore(autoreId);
    }

    /**
     * Restituisce una rappresentazione testuale dell'oggetto Raccolta.
     *
     * @return String contenente tutti i dettagli della raccolta.
     */
    @Override
    public String toString() {
        return "Raccolta{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", autoreID=" + autoreID +
                '}';
    }
}