package DTO;

import java.util.Date;
import java.util.List;

public class PoesiaDTO {

    private String titolo;
    private int autoreID;
    private String contenuto;
    private Date dataCreazione;
    private List<String> tags;
    private int id;

    public PoesiaDTO(int id, String titolo, int autoreID, String contenuto, Date dataCreazione, List<String> tags) {
        this.titolo = titolo;
        this.autoreID = autoreID;
        this.contenuto = contenuto;
        this.dataCreazione = dataCreazione;
        this.tags = tags;
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
