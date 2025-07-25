package DTO;

public class RaccoltaDTO {

    /**
     * Titolo della raccolta.
     */
    String titolo;

    /**
     * Descrizione della raccolta.
     */
    String descrizione;

    /**
     * Costruttore per creare un oggetto RaccoltaDTO.
     * Utilizzato internamente per creare istanze di raccolte recuperate dal database.
     *
     * @param titolo Titolo della raccolta.
     * @param descrizione Descrizione della raccolta.
     */
    public RaccoltaDTO(String titolo, String descrizione) {
        this.titolo = titolo;
        this.descrizione = descrizione;
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
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
