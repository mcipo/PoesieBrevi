package DTO;

import java.util.Date;

public class ProfiloDTO {

    /**
     * Nome utente del profilo.
     */
    private String username;

    /**
     * Biografia del profilo.
     */
    private String bio;

    /**
     * Percorso dell'immagine del profilo.
     */
    private String immagineProfilo;

    /**
     * Data di nascita dell'utente associato al profilo.
     */
    private Date dataNascita;

    /**
     * Costruttore per creare un oggetto ProfiloDTO.
     * Utilizzato internamente per creare istanze di profili recuperati dal database.
     *
     * @param username Nome utente del profilo.
     * @param bio Biografia del profilo.
     * @param immagineProfilo Percorso dell'immagine del profilo.
     * @param dataNascita Data di nascita dell'utente associato al profilo.
     */
    public ProfiloDTO(String username, String bio, String immagineProfilo, Date dataNascita) {
        this.username = username;
        this.bio = bio;
        this.immagineProfilo = immagineProfilo;
        this.dataNascita = dataNascita;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getImmagineProfilo() {
        return immagineProfilo;
    }
    public void setImmagineProfilo(String immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }
    public Date getDataNascita() {
        return dataNascita;
    }
    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

}
