/*import controller.PoesiaController;
import database.DatabaseConnection;
import entity.Poesia;
import entity.Raccolta;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class PoesiaControllerTest {

    private static final Logger LOGGER = Logger.getLogger(PoesiaControllerTest.class.getName());
    private final int testUserId = 1;
    private int poesiaIdCreata;
    private int raccoltaIdCreata;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
    }

    @After
    public void tearDown() throws SQLException {
        if (poesiaIdCreata > 0) {
            String query = "DELETE FROM poesie WHERE id = ?";
            int result = DatabaseConnection.executeUpdate(query, poesiaIdCreata);
            if (result < 0){
                LOGGER.log(Level.SEVERE, "tearDown - Errore nell'eliminazione della poesie dal DB");
                return;
            }
            poesiaIdCreata = 0;
        }
        if (raccoltaIdCreata > 0) {
            String query = "DELETE FROM raccolte WHERE id = ?";
            int result = DatabaseConnection.executeUpdate(query, raccoltaIdCreata);
            if (result < 0){
                LOGGER.log(Level.SEVERE, "tearDown - Errore nell'eliminazione della raccolta dal DB");
                return;
            }
            raccoltaIdCreata = 0;
        }
    }

    @Test
    public void testCreaPoesiaValida() {
        String titolo = "Test Poesia JUnit";
        String contenuto = "Questa è una poesia di test creata con JUnit.";
        String tags = "test,junit,poesia";
        boolean visibile = true;

        boolean risultato = PoesiaController.creaPoesia(titolo, contenuto, tags, visibile, testUserId, -1);

        assertTrue("La creazione di una poesia valida dovrebbe restituire true", risultato);

        try {
            poesiaIdCreata = trovaIdPoesiaPerTitolo(titolo);
            assertTrue("La poesia creata dovrebbe avere un ID valido", poesiaIdCreata > 0);
        } catch (SQLException e) {
            fail("Errore SQL durante la verifica della poesia creata: " + e.getMessage());
        }
    }
    private int trovaIdPoesiaPerTitolo(String titolo) throws SQLException{
        String query = "SELECT id FROM poesie WHERE titolo = ? AND autore_id = ? ORDER BY data_creazione DESC LIMIT 1";

        ResultSet result = DatabaseConnection.executeQuery(query, titolo, testUserId);
        if (result.next()) {
            return result.getInt("id");
        }

        return -1;
    }

    @Test
    public void testCreaPoesiaConTitoloVuoto() {
        String titolo = "";
        String contenuto = "Questa è una poesia di test con titolo vuoto.";
        String tags = "test,junit";
        boolean visibile = true;
        boolean risultato = PoesiaController.creaPoesia(titolo, contenuto, tags, visibile, testUserId, -1);
        assertFalse("La creazione di una poesia con titolo vuoto dovrebbe restituire false", risultato);
    }

    @Test
    public void testCreaPoesiaConContenutoVuoto() {
        String titolo = "Test Poesia Vuota";
        String contenuto = "";
        String tags = "test,junit";
        boolean visibile = true;

        boolean risultato = PoesiaController.creaPoesia(titolo, contenuto, tags, visibile, testUserId, -1);

        assertFalse("La creazione di una poesia con contenuto vuoto dovrebbe restituire false", risultato);
    }

    @Test
    public void testCreaPoesiaConContenutoTroppoLungo() {
        String titolo = "Test Poesia Lunga";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 51; i++) {
            sb.append("0123456789");
        }
        String contenuto = sb.toString();
        String tags = "test,junit";
        boolean visibile = true;
        boolean risultato = PoesiaController.creaPoesia(titolo, contenuto, tags, visibile, testUserId, -1);
        assertFalse("La creazione di una poesia con contenuto troppo lungo dovrebbe restituire false", risultato);
    }

    @Test
    public void testCreaRaccoltaValida() {
        String titolo = "Test Raccolta JUnit";
        String descrizione = "Questa è una raccolta di test creata con JUnit.";

        int raccoltaId = PoesiaController.creaRaccolta(titolo, descrizione, testUserId);
        assertTrue("La creazione di una raccolta valida dovrebbe restituire un ID > 0", raccoltaId > 0);
        raccoltaIdCreata = raccoltaId;
        try {
            boolean esiste = verificaEsistenzaRaccoltaId(raccoltaId);
            assertTrue("La raccolta creata dovrebbe esistere nel database", esiste);
        } catch (SQLException e) {
            fail("Errore SQL durante la verifica della raccolta creata: " + e.getMessage());
        }
    }
    private boolean verificaEsistenzaRaccoltaId(int raccoltaId) throws SQLException {
        String query = "SELECT COUNT(*) FROM raccolte WHERE id = ?";
        ResultSet result = DatabaseConnection.executeQuery(query, raccoltaId);
        return result.next() && result.getInt(1) > 0;
    }

    @Test
    public void testCreaRaccoltaConTitoloVuoto() {
        String titolo = "";
        String descrizione = "Questa è una descrizione di test.";
        int raccoltaId = PoesiaController.creaRaccolta(titolo, descrizione, testUserId);
        assertEquals("La creazione di una raccolta con titolo vuoto dovrebbe restituire -1", -1, raccoltaId);
    }

    @Test
    public void testCreaPoesiaInRaccolta() throws SQLException {
        String titoloRaccolta = "Test Raccolta per Poesie";
        String descrizioneRaccolta = "Raccolta per contenere poesie di test";

        int raccoltaId = PoesiaController.creaRaccolta(titoloRaccolta, descrizioneRaccolta, testUserId);
        assertTrue("La creazione della raccolta dovrebbe riuscire", raccoltaId > 0);
        raccoltaIdCreata = raccoltaId;

        String titoloPoesia = "Test Poesia in Raccolta";
        String contenutoPoesia = "Questa poesia appartiene a una raccolta di test.";
        String tags = "test,raccolta,junit";
        boolean visibile = true;

        boolean risultato = PoesiaController.creaPoesia(titoloPoesia, contenutoPoesia, tags, visibile, testUserId, raccoltaId);
        assertTrue("La creazione della poesia in una raccolta dovrebbe riuscire", risultato);
        poesiaIdCreata = trovaIdPoesiaPerTitolo(titoloPoesia);

        int raccoltaAssociataId = recuperaRaccoltaIdPerPoesia(poesiaIdCreata);
        assertEquals("La poesia dovrebbe essere associata alla raccolta creata", raccoltaId, raccoltaAssociataId);
    }

    private int recuperaRaccoltaIdPerPoesia(int poesiaId) throws SQLException {
        String query = "SELECT raccolta_id FROM poesie WHERE id = ?";

        ResultSet result = DatabaseConnection.executeQuery(query, poesiaId);
        if (result.next()) {
            return result.getInt("raccolta_id");
        }
        return -1;
    }

    @Test
    public void testGetPoesieByAutore() {

        String titolo = "Test Poesia per getPoesieByAutore";
        String contenuto = "Questa poesia è usata per testare il recupero delle poesie di un autore.";
        String tags = "test,recupero,junit";
        boolean visibile = true;

        boolean risultatoCreazione = PoesiaController.creaPoesia(titolo, contenuto, tags, visibile, testUserId, -1);
        assertTrue("La creazione della poesia di test dovrebbe riuscire", risultatoCreazione);

        try {
            poesiaIdCreata = trovaIdPoesiaPerTitolo(titolo);
        } catch (SQLException e) {
            fail("Errore nel trovare l'ID della poesia creata: " + e.getMessage());
        }

        List<Poesia> poesie = PoesiaController.getPoesieByAutore(testUserId);

        assertFalse("L'utente dovrebbe avere almeno una poesia", poesie.isEmpty());


        boolean trovata = false;
        for (Poesia poesia : poesie) {
            if (poesia.getTitolo().equals(titolo)) {
                trovata = true;
                break;
            }
        }
        assertTrue("La poesia appena creata dovrebbe essere nella lista", trovata);
    }

    @Test
    public void testGetRaccolteUtente() {
        String titolo = "Test Raccolta per getRaccolteUtente";
        String descrizione = "Questa raccolta è usata per testare il recupero delle raccolte di un utente.";

        int raccoltaId = PoesiaController.creaRaccolta(titolo, descrizione, testUserId);
        assertTrue("La creazione della raccolta di test dovrebbe riuscire", raccoltaId > 0);
        raccoltaIdCreata = raccoltaId;

        List<Raccolta> raccolte = PoesiaController.getRaccolteUtente(testUserId);

        assertFalse("L'utente dovrebbe avere almeno una raccolta", raccolte.isEmpty());

        boolean trovata = false;
        for (Raccolta raccolta : raccolte) {
            if (raccolta.getId() == raccoltaId) {
                trovata = true;
                assertEquals("Il titolo della raccolta dovrebbe corrispondere", titolo, raccolta.getTitolo());
                assertEquals("La descrizione della raccolta dovrebbe corrispondere", descrizione, raccolta.getDescrizione());
                break;
            }
        }
        assertTrue("La raccolta appena creata dovrebbe essere nella lista", trovata);
    }

}*/