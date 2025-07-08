package DTO;

import java.util.Date;

public class CommentoDTO {

    private String username;
    private String testo;
    private Date dataCreazione;

    public CommentoDTO(String username, String testo, Date dataCreazione) {
        this.username = username;
        this.testo = testo;
        this.dataCreazione = dataCreazione;
    }

    public CommentoDTO(String testo, Date dataCreazione) {
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
