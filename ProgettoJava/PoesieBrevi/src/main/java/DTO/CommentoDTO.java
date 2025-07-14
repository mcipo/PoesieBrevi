package DTO;

import java.util.Date;

/**
 * Data Transfer Object per i commenti sulle poesie.
 * Questa classe rappresenta un commento con il nome utente dell'autore,
 * il testo del commento e la data di creazione.
 */
public class CommentoDTO {

    /**
     * Nome utente dell'autore del commento.
     */
    private String username;

    /**
     * Testo del commento.
     */
    private String testo;

    /**
     * Data di creazione del commento.
     */
    private Date dataCreazione;

    /**
     * Costruttore per creare un oggetto CommentoDTO.
     * Utilizzato internamente per creare istanze di commenti recuperati dal database.
     *
     * @param username Nome utente dell'autore del commento.
     * @param testo Testo del commento.
     * @param dataCreazione Data di creazione del commento.
     */
    public CommentoDTO(String username, String testo, Date dataCreazione) {
        this.username = username;
        this.testo = testo;
        this.dataCreazione = dataCreazione;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }
    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getTesto() {
        return testo;
    }
    public void setTesto(String testo) {
        this.testo = testo;
    }
}
