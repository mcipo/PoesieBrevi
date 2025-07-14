package entity;

import database.DAO.CommentoDAO;

import java.util.Date;
import java.util.List;

/**
 * Questa classe rappresenta un commento lasciato da un utente su una poesia.
 * Permette l'interazione tra utenti attraverso feedback e discussioni sulle poesie pubblicate.
 */
public class Commento {
    /**
     * ID univoco del commento nel database.
     */
    private int id;
    
    /**
     * ID della poesia a cui si riferisce il commento.
     */
    private final int poesiaID;
    
    /**
     * ID dell'utente che ha scritto il commento.
     */
    private final int autoreID;
    
    /**
     * Testo del commento.
     */
    private final String testo;
    
    /**
     * Data in cui è stato creato il commento.
     */
    private final Date dataCreazione;

    /**
     * Costruttore per creare un nuovo oggetto Commento.
     *
     * @param id ID univoco del commento.
     * @param poesiaID ID della poesia commentata.
     * @param autoreID ID dell'autore del commento.
     * @param testo Contenuto testuale del commento.
     * @param dataCreazione Data di creazione del commento.
     */
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

    /**
     * Recupera tutti i commenti associati a una poesia specifica.
     *
     * @param poesiaId ID della poesia per cui si vogliono recuperare i commenti.
     * @return Lista di oggetti Commento associati alla poesia.
     */
    public static List<Commento> getCommentiByPoesiaId(int poesiaId) {

        List<CommentoDAO> commentiDAO = CommentoDAO.getCommentiByPoesiaId(poesiaId);
        return commentiDAO.stream()
                .map(dao -> new Commento(dao.getId(), dao.getPoesiaId(), dao.getAutoreId(), dao.getTesto(), dao.getDataCreazione()))
                .toList();

    }

    /**
     * Salva un nuovo commento nel database.
     *
     * @param nuovoCommento Oggetto Commento da salvare.
     * @return true se il commento è stato salvato correttamente, false altrimenti.
     */
    public static boolean salvaCommento(Commento nuovoCommento) {
        try {
            int poesiaID = nuovoCommento.getPoesiaID();
            int autoreID = nuovoCommento.getAutoreID();
            String testo = nuovoCommento.getTesto();
            Date dataCreazione = nuovoCommento.getDataCreazione();
            CommentoDAO.addCommento(poesiaID, autoreID, testo, dataCreazione);
            return true;
        } catch (Exception e) {
            return false;
        }
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