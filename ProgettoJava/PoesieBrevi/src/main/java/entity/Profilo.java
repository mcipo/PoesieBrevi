package entity;

import java.util.Date;

/**
 * Questa classe rappresenta il profilo di un utente nel sistema.
 * Contiene informazioni personali mostrate pubblicamente come username, biografia
 * e immagine del profilo.
 */
public class Profilo {
    /**
     * Nome utente univoco visualizzato pubblicamente.
     */
    private final String username;
    
    /**
     * Breve biografia o descrizione dell'utente.
     */
    private final String bio;
    
    /**
     * Percorso dell'immagine del profilo dell'utente.
     */
    private String immagineProfilo;
    
    /**
     * Data di nascita dell'utente.
     */
    private final Date dataNascita;

    /**
     * Costruttore per creare un nuovo oggetto Profilo.
     *
     * @param username Nome utente visualizzato pubblicamente.
     * @param bio Biografia o descrizione dell'utente.
     * @param immagineProfilo Percorso dell'immagine del profilo.
     * @param dataNascita Data di nascita dell'utente.
     */
    public Profilo(String username, String bio, String immagineProfilo, Date dataNascita) {
        this.username = username;
        this.bio = bio;
        this.immagineProfilo = immagineProfilo;
        this.dataNascita = dataNascita;
    }

    /**
     * Restituisce l'username dell'utente.
     *
     * @return Username dell'utente.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Restituisce la biografia dell'utente.
     *
     * @return Biografia dell'utente.
     */
    public String getBio() {
        return bio;
    }

    /**
     * Restituisce il percorso dell'immagine del profilo.
     *
     * @return Percorso dell'immagine del profilo.
     */
    public String getImmagineProfilo() {
        return immagineProfilo;
    }

    /**
     * Imposta una nuova immagine del profilo.
     *
     * @param immagineProfilo Nuovo percorso dell'immagine del profilo.
     */
    public void setImmagineProfilo(String immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }

    /**
     * Restituisce la data di nascita dell'utente.
     *
     * @return Data di nascita dell'utente.
     */
    public Date getDataNascita() {
        return dataNascita;
    }

    /**
     * Restituisce una rappresentazione testuale dell'oggetto Profilo.
     *
     * @return String contenente tutti i dettagli del profilo.
     */
    @Override
    public String toString() {
        return "Profilo{" +
                "username='" + username + '\'' +
                ", bio='" + bio + '\'' +
                ", immagineProfilo='" + immagineProfilo + '\'' +
                ", dataNascita=" + dataNascita +
                '}';
    }
}