package entity;

import java.util.Date;

public class Commento {
    private int id;
    private final int poesiaID;
    private final int autoreID;
    private final String testo;
    private final Date dataCreazione;

    public Commento(int id, int poesiaID, int autoreID, String testo, Date dataCreazione) {
        this.id = id;
        this.poesiaID = poesiaID;
        this.autoreID = autoreID;
        this.testo = testo;
        this.dataCreazione = dataCreazione;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoesiaID() {
        return poesiaID;
    }

    public int getAutoreID() {
        return autoreID;
    }

    public String getTesto() {
        return testo;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

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