package entity;

import database.DAO.PoesiaDAO;

import java.util.Date;
import java.util.List;

/**
 * Questa classe rappresenta l'entità Poesia nel sistema.
 * Contiene tutte le informazioni relative a una poesia pubblicata da un utente,
 * inclusi i tag, la visibilità e l'eventuale appartenenza a una raccolta.
 */
public class Poesia {
    /**
     * ID univoco della poesia nel database.
     */
    private int id;
    
    /**
     * Titolo della poesia.
     */
    private String titolo;
    
    /**
     * Contenuto testuale della poesia.
     */
    private final String contenuto;
    
    /**
     * Lista di tag associati alla poesia per facilitarne la ricerca.
     */
    private final List<String> tags;
    
    /**
     * Flag che indica se la poesia è visibile pubblicamente o privata.
     */
    private final boolean visibile;
    
    /**
     * Data di creazione della poesia.
     */
    private final Date dataCreazione;
    
    /**
     * ID dell'utente autore della poesia.
     */
    private final int autoreID;
    
    /**
     * ID della raccolta a cui appartiene la poesia, se presente.
     * Un valore -1 indica che non appartiene a nessuna raccolta.
     */
    private final int raccoltaID;

    /**
     * Costruttore per creare un nuovo oggetto Poesia con tutti i suoi attributi.
     *
     * @param id ID della poesia.
     * @param titolo Titolo della poesia.
     * @param contenuto Testo della poesia.
     * @param tags Lista di tag associati alla poesia.
     * @param visibile Indica se la poesia è visibile pubblicamente.
     * @param dataCreazione Data di creazione della poesia.
     * @param autoreID ID dell'autore della poesia.
     * @param raccoltaID ID della raccolta a cui appartiene (se presente).
     */
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

    /**
     * Recupera tutte le poesie create da un determinato autore.
     *
     * @param autoreId ID dell'utente autore delle poesie.
     * @return Lista delle poesie dell'utente.
     */
    public static List<Poesia> getPoesieByAutore(int autoreId) {
        List<PoesiaDAO> poesieDAO = PoesiaDAO.getPoesieByAutore(autoreId);;
        return poesieDAO.stream()
                .map(dao -> new Poesia(dao.getId(), dao.getTitolo(), dao.getContenuto(), dao.getTags(), dao.getIsVisibile(), dao.getDataCreazione(), dao.getAutoreID(), dao.getRaccoltaID() ))
                .toList();
    }

    /**
     * Recupera le ultime poesie visibili per il feed dell'utente, escludendo quelle dell'utente stesso.
     *
     * @param userId ID dell'utente per cui recuperare le poesie.
     * @param limit Numero massimo di poesie da recuperare.
     * @return Lista di oggetti Poesia contenenti le poesie recenti.
     */
    public static List<Poesia> getUltimePoesiePerFeed(int userId, int limit) {
        List<PoesiaDAO> poesieDAO = PoesiaDAO.getUltimePoesiePerFeed(userId, limit);
        return poesieDAO.stream()
                .map(dao -> new Poesia(dao.getId(), dao.getTitolo(), dao.getContenuto(), dao.getTags(), dao.getIsVisibile(), dao.getDataCreazione(), dao.getAutoreID(), dao.getRaccoltaID() ))
                .toList();
    }

    /**
     * Salva una nuova poesia nel database.
     *
     * @return true se la poesia è stata salvata correttamente, false altrimenti.
     */
    public boolean salvaPoesia() {
        return PoesiaDAO.addPoesia(this.getTitolo(), this.contenuto, this.visibile, this.dataCreazione, this.tags, this.autoreID, this.raccoltaID);
    }

    /**
     * Recupera le poesie create negli ultimi 'giorni' giorni.
     *
     * @param giorni Numero di giorni per cui recuperare le poesie.
     * @return Lista delle poesie create negli ultimi 'giorni' giorni.
     */
    public static List<Poesia> getPoesieInIntervallo(int giorni){
        List<PoesiaDAO> poesieDAO = PoesiaDAO.getPoesieInIntervallo(giorni);
        return poesieDAO.stream()
                .map(dao -> new Poesia(dao.getId(), dao.getTitolo(), dao.getContenuto(), dao.getTags(), dao.getIsVisibile(), dao.getDataCreazione(), dao.getAutoreID(), dao.getRaccoltaID() ))
                .toList();
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

