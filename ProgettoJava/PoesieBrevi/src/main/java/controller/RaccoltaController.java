package controller;

import entity.Raccolta;

import java.util.List;

public class RaccoltaController {

    private RaccoltaController(){

    }

    /**
     * Crea una nuova raccolta di poesie.
     *
     * @param titolo Titolo della raccolta.
     * @param descrizione Descrizione della raccolta.
     * @param autoreId ID dell'utente che ha creato la raccolta.
     * @return ID della raccolta creata, o -1 se la creazione è fallita.
     */
    public static int creaRaccolta(String titolo, String descrizione, int autoreId) {

        if (titolo == null || titolo.trim().isEmpty()) {
            return -1;
        }


        Raccolta nuovaRaccolta = new Raccolta(0, titolo, descrizione, autoreId);
        return nuovaRaccolta.salvaRaccolta();
    }


    /**
     * Recupera tutte le raccolte create da un determinato utente.
     *
     * @param autoreId ID dell'utente autore delle raccolte.
     * @return Lista delle raccolte dell'utente.
     */
    public static List<Raccolta> getRaccolteUtente(int autoreId) {
        return Raccolta.getRaccoltaByAutore(autoreId);
    }

}
