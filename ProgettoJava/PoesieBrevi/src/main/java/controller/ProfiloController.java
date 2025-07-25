package controller;

import DTO.ProfiloDTO;
import entity.Profilo;
import entity.User;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

/**
 * Controller che gestisce la logica relativa ai profili utente.
 * Fornisce funzionalità per la validazione, il salvataggio e il caricamento dei profili,
 * inclusa la gestione delle immagini del profilo.
 */
public class ProfiloController {

    private ProfiloController(){}
    
    /**
     * Directory dove vengono salvate le immagini dei profili.
     */
    private static final String IMG_DIRECTORY = "resources/immagine_profilo/";
    
    /**
     * Valida i dati del profilo utente secondo le regole di business.
     * Verifica che l'username sia valido, la biografia non superi i limiti
     * e che l'utente abbia almeno 13 anni.
     *
     * @param username Username dell'utente.
     * @param bio Biografia dell'utente.
     * @param dataNascita Data di nascita dell'utente.
     * @return true se i dati sono validi, false altrimenti.
     */
    public static boolean validaDatiProfilo(String username, String bio, Date dataNascita) {
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
    

    /**
     * Salva le modifiche apportate al profilo di un utente.
     * Se il profilo contiene una nuova immagine, la copia nella directory appropriata.
     * Se l'utente non ha un profilo esistente, ne crea uno nuovo.
     *
     * @param user Utente proprietario del profilo.
     * @param profiloDTO Profilo con le modifiche da salvare.
     * @return true se le modifiche sono state salvate con successo, false altrimenti.
     */
    public static boolean salvaModificheProfilo(User user, ProfiloDTO profiloDTO) {
        if (user == null || profiloDTO == null) {
            return false;
        }
        String username = profiloDTO.getUsername();
        String bio = profiloDTO.getBio();
        Date dataNascita = profiloDTO.getDataNascita();
        try {
            if (!validaDatiProfilo(username, bio, dataNascita)) {
                return false;
            }
            

            String percorsoImmagine = profiloDTO.getImmagineProfilo();
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

                        profiloDTO.setImmagineProfilo(nuovoPercorso);
                    }
                }
            }

            Profilo profilo = new Profilo(username, bio, percorsoImmagine ,dataNascita);
            
            user.setProfilo(profilo);

            Profilo existingProfile = Profilo.getProfiloAtID(user.getId());
            if (existingProfile != null) {

                profilo.updateProfilo(user.getId());
            } else {
                profilo.createProfilo(user.getId());
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Recupera lo username di un utente dato il suo ID.
     *
     * @param userId ID dell'utente.
     * @return Username dell'utente, o "Sconosciuto" se non trovato.
     */
    public static String getUsernameByUserId(int userId) {
        Profilo profilo = Profilo.getProfiloAtID(userId);
        return profilo != null ? profilo.getUsername() : "Sconosciuto";
    }
    

    /**
     * Carica il profilo di un utente dal database.
     * Se l'utente ha già un profilo caricato in memoria, lo restituisce direttamente.
     *
     * @param user Utente di cui caricare il profilo.
     * @return Il profilo dell'utente, o null se non esiste o si verifica un errore.
     */
    public static ProfiloDTO caricaProfilo(User user) {
        if (user == null) {
            return null;
        }
        
        if (user.getProfilo() != null) {
            Profilo profilo = user.getProfilo();

            String username = profilo.getUsername();
            String bio = profilo.getBio();
            String percorsoImmagine = profilo.getImmagineProfilo();
            Date dataNascita = profilo.getDataNascita();

            return new ProfiloDTO(username, bio, percorsoImmagine, dataNascita);
        }
        
        return null;
    }

    /**
     * Carica il profilo di un utente dato il suo ID.
     * Se l'ID è negativo, restituisce null.
     *
     * @param userId ID dell'utente di cui caricare il profilo.
     * @return Il profilo dell'utente come ProfiloDTO, o null se non esiste o si verifica un errore.
     */
    public static ProfiloDTO caricaProfilo(int userId) {

        if (userId < 0) {
            return null;
        }
        Profilo profilo = Profilo.getProfiloAtID(userId);
        String username = profilo.getUsername();
        String bio = profilo.getBio();
        Date dataNascita = profilo.getDataNascita();
        String immagineProfilo = profilo.getImmagineProfilo();
        ProfiloDTO profiloDTO = new ProfiloDTO(username,bio, immagineProfilo, dataNascita);
        return profiloDTO;

    }
}