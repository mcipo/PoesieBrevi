package DTO;

import java.util.Date;
import java.util.List;

public class PoesiaDTO {

    /**
     * Titolo della poesia.
     */
    private String titolo;

    /**
     * ID dell'autore della poesia.
     */
    private int autoreID;

    /**
     * Contenuto della poesia.
     */
    private String contenuto;

    /**
     * Data di creazione della poesia.
     */
    private Date dataCreazione;

    /**
     * Lista dei tag associati alla poesia.
     */
    private List<String> tags;

    /**
     * ID della poesia.
     */
    private int id;

    /**
     * Costruttore per creare un oggetto PoesiaDTO.
     * Utilizzato internamente per creare istanze di poesie recuperate dal database.
     *
     * @param id ID della poesia.
     * @param titolo Titolo della poesia.
     * @param autoreID ID dell'autore della poesia.
     * @param contenuto Contenuto della poesia.
     * @param dataCreazione Data di creazione della poesia.
     * @param tags Lista dei tag associati alla poesia.
     */
    public PoesiaDTO(int id, String titolo, int autoreID, String contenuto, Date dataCreazione, List<String> tags) {
        this.titolo = titolo;
        this.autoreID = autoreID;
        this.contenuto = contenuto;
        this.dataCreazione = dataCreazione;
        this.tags = tags;
        this.id = id;
    }

    /**
     * Costruttore per creare un oggetto PoesiaDTO con solo titolo e autore.
     * Utilizzato per creare nuove poesie da aggiungere al database.
     *
     * @param id ID della poesia.
     * @param titolo Titolo della poesia.
     * @param autoreID ID dell'autore della poesia.
     */
    public PoesiaDTO(int id, String titolo, int autoreID) {
        this.titolo = titolo;
        this.autoreID = autoreID;
        this.id = id;
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
    public int getAutoreID() {
        return autoreID;
    }
    public void setAutoreID(int autore) {
        this.autoreID = autore;
    }
    public String getContenuto() {
        return contenuto;
    }
    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }
    public Date getDataCreazione() {
        return dataCreazione;
    }
    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
