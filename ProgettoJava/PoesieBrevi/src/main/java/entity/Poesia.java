package entity;

import java.util.Date;
import java.util.List;

public class Poesia {
    private int id;
    private String titolo;
    private final String contenuto;
    private final List<String> tags;
    private final boolean visibile;
    private final Date dataCreazione;
    private final int autoreID;
    private final int raccoltaID;

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

    public String getContenuto() {
        return contenuto;
    }

    public List<String> getTags() {
        return tags;
    }

    public boolean getVisibile() {
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

