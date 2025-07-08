package controller;

import DTO.RaccoltaDTO;
import entity.Raccolta;

import java.util.ArrayList;
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
    public static List<RaccoltaDTO> getRaccolteUtente(int autoreId) {
        List<Raccolta> raccolte = Raccolta.getRaccoltaByAutore(autoreId);
        List<RaccoltaDTO> raccolteDTO = new ArrayList<>();
        for (Raccolta raccolta : raccolte) {
            String titolo = raccolta.getTitolo();
            String descrizione = raccolta.getDescrizione();
            raccolteDTO.add(new RaccoltaDTO(titolo, descrizione));
        }
        return raccolteDTO;
    }

}
