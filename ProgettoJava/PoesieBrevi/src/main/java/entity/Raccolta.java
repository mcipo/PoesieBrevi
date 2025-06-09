package entity;

public class Raccolta {
    private int id;
    private String titolo;
    private final String descrizione;
    private final int autoreID;

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