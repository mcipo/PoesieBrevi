package controller;

import database.DAO.*;
import entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PoesiaController {


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
        
        return PoesiaDAO.addPoesia(nuovaPoesia);
    }
    

    public static int creaRaccolta(String titolo, String descrizione, int autoreId) {

        if (titolo == null || titolo.trim().isEmpty()) {
            return -1;
        }
        

        Raccolta nuovaRaccolta = new Raccolta(0, titolo, descrizione, autoreId);
        return RaccoltaDAO.addRaccolta(nuovaRaccolta);
    }
    

    public static List<Raccolta> getRaccolteUtente(int autoreId) {
        return RaccoltaDAO.getRaccoltaPerAutore(autoreId);
    }
    

    public static List<Poesia> getPoesieByAutore(int autoreId) {
        return PoesiaDAO.getPoesieByAutore(autoreId);
    }
    

    public static List<Poesia> getUltimePoesiePerFeed(int userId, int limit) {
        return PoesiaDAO.getUltimePoesiePerFeed(userId, limit);
    }
    

    public static String getUsernameByUserId(int userId) {
        Profilo profilo = ProfiloDAO.getProfiloAtID(userId);
        return profilo != null ? profilo.getUsername() : "Sconosciuto";
    }
    

    public static int getNumCuori(int poesiaId) {
        return CuoreDAO.getNumCuori(poesiaId);
    }

    public static int getNumCommenti(int poesiaId) throws SQLException {;
        int numCommenti = CommentoDAO.getCommentiByPoesiaId(poesiaId).size();
        return numCommenti;
    }

    public static List<Commento> getCommenti(int poesiaId) throws SQLException {
        List<Commento> commenti = CommentoDAO.getCommentiByPoesiaId(poesiaId);
        return commenti;
    }

    public static boolean salvaCommento(Commento nuovoCommento) {
        try {
            CommentoDAO.addCommento(nuovoCommento);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasUserCuorePoesia(int poesiaId, int userId) {

        return CuoreDAO.hasUserLiked(poesiaId, userId);
    }
    

    public static boolean toggleCuore(int poesiaId, int userId) {
        if (hasUserCuorePoesia(poesiaId, userId)) {
            return CuoreDAO.removeCuore(poesiaId, userId);
        } else {
            return CuoreDAO.addCuore(poesiaId, userId);
        }
    }
}
