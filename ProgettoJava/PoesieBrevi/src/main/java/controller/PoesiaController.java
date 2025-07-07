package controller;

import entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Controller che gestisce la logica relativa alle poesie e alle raccolte.
 * Fornisce funzionalità per la creazione, recupero e interazione con poesie e raccolte,
 * inclusa la gestione di commenti e "cuori" (mi piace).
 */
public class PoesiaController {

    private PoesiaController(){

    }

    /**
     * Crea una nuova poesia nel sistema.
     *
     * @param titolo Titolo della poesia.
     * @param contenuto Testo della poesia.
     * @param tags Tag associati alla poesia, come stringa separata da virgole.
     * @param visibile Indica se la poesia è visibile pubblicamente.
     * @param autoreId ID dell'utente che ha creato la poesia.
     * @param raccoltaId ID della raccolta a cui appartiene la poesia, o -1 se non appartiene a nessuna raccolta.
     * @return true se la creazione è avvenuta con successo, false altrimenti.
     */
    public static boolean creaPoesia(String titolo, String contenuto, String tags, boolean visibile, int autoreId, int raccoltaId) {

        if (titolo == null || titolo.trim().isEmpty() || contenuto == null || contenuto.trim().isEmpty()) {
            return false;
        }
        
        if (contenuto.length() > 500) {
            return false;
        }
        

        List<String> tagList = new ArrayList<>();
        if (tags != null && !tags.trim().isEmpty()) {
            tagList = Arrays.asList(tags.split(","));

            for (int i = 0; i < tagList.size(); i++) {
                tagList.set(i, tagList.get(i).trim());
            }
        }

        Poesia nuovaPoesia = new Poesia(
            0,
            titolo,
            contenuto,
            tagList,
            visibile,
            new Date(),
            autoreId,
            raccoltaId == -1 ? 0 : raccoltaId
        );
        
        return nuovaPoesia.salvaPoesia();
    }
    


    

    

    /**
     * Recupera tutte le poesie create da un determinato utente.
     *
     * @param autoreId ID dell'utente autore delle poesie.
     * @return Lista delle poesie dell'utente.
     */
    public static List<Poesia> getPoesieByAutore(int autoreId) {
        return Poesia.getPoesieByAutore(autoreId);
    }
    

    /**
     * Recupera le poesie più recenti per il feed dell'utente.
     *
     * @param userId ID dell'utente che visualizza il feed.
     * @param limit Numero massimo di poesie da recuperare.
     * @return Lista delle poesie più recenti.
     */
    public static List<Poesia> getUltimePoesiePerFeed(int userId, int limit) {
        return Poesia.getUltimePoesiePerFeed(userId, limit);
    }
    


    

    /**
     * Conta il numero di "cuori" (mi piace) ricevuti da una poesia.
     *
     * @param poesiaId ID della poesia.
     * @return Numero di cuori ricevuti dalla poesia.
     */
    public static int getNumCuori(int poesiaId) {
        return Cuore.getNumCuori(poesiaId);
    }

    /**
     * Conta il numero di commenti ricevuti da una poesia.
     *
     * @param poesiaId ID della poesia.
     * @return Numero di commenti ricevuti dalla poesia.
     * @throws SQLException Se si verifica un errore durante l'operazione sul database.
     */
    public static int getNumCommenti(int poesiaId) throws SQLException {;
        int numCommenti = Commento.getCommentiByPoesiaId(poesiaId).size();
        return numCommenti;
    }

    /**
     * Recupera tutti i commenti relativi a una poesia.
     *
     * @param poesiaId ID della poesia.
     * @return Lista dei commenti della poesia.
     * @throws SQLException Se si verifica un errore durante l'operazione sul database.
     */
    public static List<Commento> getCommenti(int poesiaId) throws SQLException {
        List<Commento> commenti = Commento.getCommentiByPoesiaId(poesiaId);
        return commenti;
    }

    /**
     * Salva un nuovo commento nel database.
     *
     * @param nuovoCommento Oggetto Commento da salvare.
     * @return true se il salvataggio è avvenuto con successo, false altrimenti.
     */
    public static boolean salvaCommento(Commento nuovoCommento) {
        return Commento.salvaCommento(nuovoCommento);
    }

    /**
     * Verifica se un utente ha messo un "cuore" (mi piace) a una poesia.
     *
     * @param poesiaId ID della poesia.
     * @param userId ID dell'utente.
     * @return true se l'utente ha messo un cuore alla poesia, false altrimenti.
     */
    public static boolean hasUserCuorePoesia(int poesiaId, int userId) {

        return Cuore.hasUserLiked(poesiaId, userId);
    }
    

    /**
     * Applica o rimuove un "cuore" (mi piace) da una poesia.
     * Se l'utente ha già messo un cuore, lo rimuove; altrimenti lo aggiunge.
     *
     * @param poesiaId ID della poesia.
     * @param userId ID dell'utente che mette/rimuove il cuore.
     * @return true se l'operazione è avvenuta con successo, false altrimenti.
     */
    public static boolean toggleCuore(int poesiaId, int userId) {
        if (hasUserCuorePoesia(poesiaId, userId)) {
            return Cuore.removeCuore(poesiaId, userId);
        } else {
            return Cuore.addCuore(poesiaId, userId);
        }
    }
}
