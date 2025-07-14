package UnitTest;

import controller.PiattaformaController;
import controller.PoesiaController;
import entity.User;
import DTO.PoesiaDTO;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PiattaformaControllerTest {

    private User testUser;
    private String testEmail = "test@email.com";
    private String testPassword = "test";

    @Before
    public void setup() {
        testUser = PiattaformaController.creaUtenteInMemoria("Test", "User", testEmail, testPassword, false);
        assertNotNull(testUser);

        if (!PiattaformaController.esisteUtente(testEmail)) {
            boolean saved = PiattaformaController.salvaUtente(testUser);
            assertTrue("Salvataggio utente di test fallito", saved);
        }


    }

    @Test
    public void testAutenticaUtente_Valid() {
        PiattaformaController.autenticaUtente(testEmail, testPassword);
    }

    @Test
    public void testAutenticaUtente_Invalid() {
        boolean authenticated = PiattaformaController.autenticaUtente(testEmail, "wrongpassword");
        assertFalse(authenticated);

        boolean authenticated2 = PiattaformaController.autenticaUtente("wrong@example.com", testPassword);
        assertFalse(authenticated2);
    }

    @Test
    public void testEsisteUtente() {
        assertTrue(PiattaformaController.esisteUtente(testEmail));
        assertFalse(PiattaformaController.esisteUtente("nonexistent@example.com"));
    }

    @Test
    public void testCreaUtenteInMemoria() {
        User user = PiattaformaController.creaUtenteInMemoria("TestCreaUtente", "TestCreaUtente", "TestCreaUtente@test.com", "test", false);
        assertNotNull(user);
        assertEquals("TestCreaUtente", user.getNome());
        assertEquals("TestCreaUtente", user.getCognome());
        assertEquals("TestCreaUtente@test.com", user.getEmail());
        assertEquals("test", user.getPassword());
        assertFalse(user.isAdmin());
        assertNotNull(user.getProfilo());
    }

    @Test
    public void testSalvaUtente() {
        User user = PiattaformaController.creaUtenteInMemoria("TestSalvaUtente", "TestSalvaUtente", "TestSalvaUtente@test.com", "test", false);
        boolean exists = PiattaformaController.esisteUtente("TestSalvaUtente@test.com");
        boolean saved;
        if (!exists) {
            saved = PiattaformaController.salvaUtente(user);
            assertTrue(saved);
        }
    }

    @Test
    public void testGetPoesieUltimaSettimana() {

        PoesiaController.creaPoesia(
                "Poesia test",
                "Contenuto di test",
                "test",
                true,
                PiattaformaController.getInstance().getCurrentUser().getId(),
                7
        );
        List<PoesiaDTO> poesie = PiattaformaController.getPoesieUltimaSettimana();
        assertNotNull(poesie);
        assertTrue("La lista delle poesie dovrebbe contenere almeno una poesia", poesie.size() > 0);
    }

    @Test
    public void testGetUserConPiuPoesie() {
        List<int[]> userStats = PiattaformaController.getUserConPiuPoesie();
        assertNotNull(userStats);
        if (!userStats.isEmpty()) {
            assertTrue(userStats.getFirst().length >= 2);
        }
    }
}
