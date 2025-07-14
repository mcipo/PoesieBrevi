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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getAutoreID() {
        return autoreID;
    }

    /**
     * Salva la raccolta nel database.
     * Questo metodo viene utilizzato per aggiungere una nuova raccolta o aggiornare una esistente.
     *
     * @return ID della raccolta salvata nel database.
     */
    public int salvaRaccolta() {
        return RaccoltaDAO.addRaccolta(this.titolo, this.descrizione, this.autoreID);
    }

    /**
     * Recupera tutte le raccolte create da un determinato utente tramite il suo ID.
     *
     * @param autoreId ID dell'utente autore delle raccolte.
     * @return Lista di oggetti Raccolta associati all'utente.
     */
    public static List<Raccolta> getRaccoltaByAutore(int autoreId) {
        return RaccoltaDAO.getRaccoltaPerAutore(autoreId);
    }

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