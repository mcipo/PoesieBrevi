package entity;

import database.DAO.ProfiloDAO;

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

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
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

    /**
     * Aggiorna le informazioni del profilo nel database.
     *
     * @param userId ID dell'utente il cui profilo deve essere aggiornato.
     */
    public void updateProfilo(int userId) {
        ProfiloDAO.updateProfilo(this.username, this.bio, this.immagineProfilo, this.dataNascita, userId);
    }

    /**
     * Crea un nuovo profilo nel database per l'utente specificato.
     *
     * @param userId ID dell'utente per cui creare il profilo.
     */
    public void createProfilo(int userId) {
        ProfiloDAO.createProfilo(this.username, this.bio, this.immagineProfilo, this.dataNascita, userId);
    }

    /**
     * Aggiorna il profilo di un utente.
     *
     * @param user L'utente il cui profilo deve essere aggiornato.
     * @param profiloDTO Oggetto contenente i nuovi dati del profilo.
     * @return true se l'aggiornamento è riuscito, false altrimenti.
     */
    public static Profilo getProfiloAtID(int userId){
        try{
            ProfiloDAO profiloDAO = ProfiloDAO.getProfiloAtID(userId);
            return new Profilo(profiloDAO.getUsername(), profiloDAO.getBio(), profiloDAO.getImmagineProfilo(), profiloDAO.getDataNascita());
        }catch (Exception e){

        }
        return null;
    }

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