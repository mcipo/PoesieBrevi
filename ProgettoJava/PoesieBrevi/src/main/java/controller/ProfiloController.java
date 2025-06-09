package controller;

import entity.Profilo;
import entity.User;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

public class ProfiloController {
    
    private static final String IMG_DIRECTORY = "resources/immagine_profilo/";
    

    public boolean validaDatiProfilo(String username, String bio, Date dataNascita) {
        if (username == null || username.trim().isEmpty() || username.length() < 3 || username.length() > 30) {
            return false;
        }
        
        if (bio == null || bio.trim().isEmpty() || bio.length() > 500) {
            return false;
        }
        
        if (dataNascita == null || dataNascita.after(new Date())) {
            return false;
        }

        Date oggi = new Date();
        long differenzaMillisecondi = oggi.getTime() - dataNascita.getTime();
        long anni = differenzaMillisecondi / (1000L * 60 * 60 * 24 * 365);
        return anni >= 13;
    }
    

    public boolean salvaModificheProfilo(User user, Profilo profilo) {
        if (user == null || profilo == null) {
            return false;
        }
        
        try {
            if (!validaDatiProfilo(profilo.getUsername(), profilo.getBio(), profilo.getDataNascita())) {
                return false;
            }
            

            String percorsoImmagine = profilo.getImmagineProfilo();
            if (percorsoImmagine != null && !percorsoImmagine.isEmpty()) {
                if (!percorsoImmagine.startsWith(IMG_DIRECTORY)) {
                    File sourceFile = new File(percorsoImmagine);
                    if (sourceFile.exists()) {
                        File directory = new File(IMG_DIRECTORY);
                        if (!directory.exists()) {
                            directory.mkdirs();
                        }
                        
                        String estensione = percorsoImmagine.substring(percorsoImmagine.lastIndexOf("."));
                        String nuovoNomeFile = UUID.randomUUID() + estensione;
                        String nuovoPercorso = IMG_DIRECTORY + nuovoNomeFile;
                        
                        Path sourcePath = sourceFile.toPath();
                        Path destinationPath = Paths.get(nuovoPercorso);
                        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        
                        profilo.setImmagineProfilo(nuovoPercorso);
                    }
                }
            }
            
            user.setProfilo(profilo);
            
            database.DAO.ProfiloDAO profiloDAO = new database.DAO.ProfiloDAO();
            

            Profilo existingProfile = profiloDAO.getProfiloAtID(user.getId());
            if (existingProfile != null) {

                profiloDAO.updateProfilo(profilo, user.getId());
            } else {

                profiloDAO.createProfilo(profilo, user.getId());
            }
            
            System.out.println("Profilo aggiornato per l'utente ID: " + user.getId());
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public Profilo caricaProfilo(User user) {
        if (user == null) {
            return null;
        }
        
        if (user.getProfilo() != null) {
            return user.getProfilo();
        }
        
        database.DAO.ProfiloDAO profiloDAO = new database.DAO.ProfiloDAO();
        Profilo profilo = profiloDAO.getProfiloAtID(user.getId());
        
        if (profilo != null) {
            user.setProfilo(profilo);
            return profilo;
        }
        
        return null;
    }
}