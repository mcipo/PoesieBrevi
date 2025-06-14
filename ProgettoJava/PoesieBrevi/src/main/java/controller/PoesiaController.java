package controller;

import database.DAO.*;
import entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PoesiaController {
    
    private final PoesiaDAO poesiaDAO;
    private final RaccoltaDAO raccoltaDAO;
    private final CuoreDAO cuoreDAO;
    private final ProfiloDAO profiloDAO;
    
    public PoesiaController() {
        this.poesiaDAO = new PoesiaDAO();
        this.raccoltaDAO = new RaccoltaDAO();
        this.cuoreDAO = new CuoreDAO();
        this.profiloDAO = new ProfiloDAO();
    }

    public boolean creaPoesia(String titolo, String contenuto, String tags, boolean visibile, int autoreId, int raccoltaId) {

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
        
        return poesiaDAO.addPoem(nuovaPoesia);
    }
    

    public int creaRaccolta(String titolo, String descrizione, int autoreId) {

        if (titolo == null || titolo.trim().isEmpty()) {
            return -1;
        }
        

        Raccolta nuovaRaccolta = new Raccolta(0, titolo, descrizione, autoreId);
        return raccoltaDAO.addRaccolta(nuovaRaccolta);
    }
    

    public List<Raccolta> getRaccolteUtente(int autoreId) {
        return raccoltaDAO.getRaccoltaPerAutore(autoreId);
    }
    

    public List<Poesia> getPoesieByAutore(int autoreId) {
        return poesiaDAO.getPoesieByAutore(autoreId);
    }
    

    public List<Poesia> getUltimePoesiePerFeed(int userId, int limit) {
        return poesiaDAO.getUltimePoesiePerFeed(userId, limit);
    }
    

    public String getUsernameByUserId(int userId) {
        Profilo profilo = profiloDAO.getProfiloAtID(userId);
        return profilo != null ? profilo.getUsername() : "Sconosciuto";
    }
    

    public int getNumCuori(int poesiaId) {
        return cuoreDAO.getNumCuori(poesiaId);
    }

    public int getNumCommenti(int poesiaId) throws SQLException {
        CommentoDAO commentoDAO = new CommentoDAO();
        int numCommenti = commentoDAO.getCommentiByPoesiaId(poesiaId).size();
        return numCommenti;
    }

    public List<Commento> getCommenti(int poesiaId) throws SQLException {
        CommentoDAO commentoDAO = new CommentoDAO();
        List<Commento> commenti = commentoDAO.getCommentiByPoesiaId(poesiaId);
        return commenti;
    }

    public boolean salvaCommento(Commento nuovoCommento) {
        CommentoDAO commentoDAO = new CommentoDAO();
        try {
            commentoDAO.addCommento(nuovoCommento);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasUserCuorePoesia(int poesiaId, int userId) {

        return cuoreDAO.hasUserLiked(poesiaId, userId);
    }
    

    public boolean toggleCuore(int poesiaId, int userId) {
        if (hasUserCuorePoesia(poesiaId, userId)) {
            return cuoreDAO.removeCuore(poesiaId, userId);
        } else {
            return cuoreDAO.addCuore(poesiaId, userId);
        }
    }
}
