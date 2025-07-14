package entity;

import database.DAO.CuoreDAO;

/**
 * Questa classe rappresenta l'entità Cuore nel sistema.
 * Gestisce le operazioni relative ai "cuori" (like) delle poesie,
 * permettendo agli utenti di esprimere apprezzamento per le poesie pubblicate.
 */
public class Cuore {
    /**
     * Costruttore privato per evitare istanziazioni dirette.
     * Utilizzato per raggruppare metodi statici relativi ai "cuori" (like) delle poesie.
     */
    private Cuore(){}

    /**
     * Rimuove un cuore (like) da una poesia specifica per un utente.
     *
     * @param poesia_id ID della poesia da cui rimuovere il cuore.
     * @param user_id ID dell'utente che ha messo il cuore.
     * @return true se il cuore è stato rimosso correttamente, false altrimenti.
     */
    public static boolean removeCuore(int poesia_id, int user_id){
        return CuoreDAO.removeCuore(poesia_id, user_id);
    }

    /**
     * Verifica se un utente ha già messo un cuore (like) su una poesia.
     *
     * @param poesia_id ID della poesia da verificare.
     * @param user_id ID dell'utente da verificare.
     * @return true se l'utente ha già messo un cuore, false altrimenti.
     */
    public static boolean addCuore(int poesia_id, int user_id){
        return CuoreDAO.addCuore(poesia_id, user_id);
    }

    /**
     * Controlla se un utente ha già messo un cuore (like) su una poesia.
     *
     * @param poesia_id ID della poesia da verificare.
     * @param user_id ID dell'utente da verificare.
     * @return true se l'utente ha già messo un cuore, false altrimenti.
     */
    public static boolean hasUserLiked(int poesia_id, int user_id){
        return CuoreDAO.hasUserLiked(poesia_id, user_id);
    }

    /**
     * Conta il numero di cuori (like) ricevuti da una poesia.
     *
     * @param poesiaId ID della poesia di cui contare i cuori.
     * @return Numero di cuori ricevuti dalla poesia, o -1 in caso di errore.
     */
    public static int getNumCuori(int poesiaId) {
        return CuoreDAO.getNumCuori(poesiaId);
    }
}
