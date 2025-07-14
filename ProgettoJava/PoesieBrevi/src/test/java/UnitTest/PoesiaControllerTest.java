package UnitTest;

import DTO.CommentoDTO;
import DTO.PoesiaDTO;

import java.util.Date;
import java.util.List;

import controller.PiattaformaController;
import controller.PoesiaController;
import entity.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PoesiaControllerTest {

    private User user = User.getUserFromDB("test@email.com");
    private int testRaccoltaId = 7;
    private int testPoesiaId;

    @Before
    public void setup() {

        PiattaformaController piattaformaController = PiattaformaController.getInstance();
        piattaformaController.setCurrentUser(user);

        boolean created = PoesiaController.creaPoesia(
                "Test titolo",
                "Test contenuto",
                "amore, pace",
                true,
                user.getId(),
                testRaccoltaId);
        assertTrue("La creazione della poesia di test deve essere riuscita.", created);

        List<PoesiaDTO> poesie = PoesiaController.getPoesieByAutore(user.getId());
        assertFalse("La lista delle poesie non deve essere vuota.", poesie.isEmpty());
        testPoesiaId = poesie.get(0).getId();
    }

    @Test
    public void testCreaPoesia_Valid() {
        boolean result = PoesiaController.creaPoesia(
                "Nuova poesia",
                "Questo è il contenuto",
                "test, junit",
                true,
                user.getId(),
                -1);
        assertTrue(result);
    }

    @Test
    public void testCreaPoesia_InvalidTitoloOrContenuto() {
        assertFalse(PoesiaController.creaPoesia("", "Valido", null, true, user.getId(), -1));
        assertFalse(PoesiaController.creaPoesia("Titolo", "", null, true, user.getId(), -1));
        assertFalse(PoesiaController.creaPoesia(null, "Valido", null, true, user.getId(), -1));
        assertFalse(PoesiaController.creaPoesia("Titolo", null, null, true, user.getId(), -1));
    }

    @Test
    public void testCreaPoesia_TooLongContent() {
        String longContent = "a".repeat(501);
        assertFalse(PoesiaController.creaPoesia("Titolo", longContent, null, true, user.getId(), -1));
    }

    @Test
    public void testGetPoesieByAutore() {
        List<PoesiaDTO> poesie = PoesiaController.getPoesieByAutore(user.getId());
        assertNotNull(poesie);
        assertFalse(poesie.isEmpty());
        assertEquals(user.getId(), poesie.getFirst().getAutoreID());
    }


    @Test
    public void testGetUltimePoesiePerFeed() {
        List<PoesiaDTO> poesie = PoesiaController.getUltimePoesiePerFeed(user.getId(), 5);
        assertNotNull(poesie);
        assertTrue(poesie.size() <= 5);
    }

    @Test
    public void testGetNumCuori() {
        int cuori = PoesiaController.getNumCuori(testPoesiaId);
        assertTrue(cuori >= 0);
    }

    @Test
    public void testGetNumCommenti() {
        int numCommenti = PoesiaController.getNumCommenti(testPoesiaId);
        assertTrue(numCommenti >= 0);
    }

    @Test
    public void testGetCommenti() throws Exception {
        CommentoDTO commentoDTO = new CommentoDTO("TestUser", "Testo commento", new Date());
        boolean res = PoesiaController.salvaCommento(commentoDTO, testPoesiaId);
        assertTrue(res);

        List<CommentoDTO> commenti = PoesiaController.getCommenti(testPoesiaId);
        assertNotNull(commenti);
        assertTrue(!commenti.isEmpty());
    }

    @Test
    public void testSalvaCommento() {
        CommentoDTO commentoDTO = new CommentoDTO("TestUser", "Testo commento JUnit", new Date());
        boolean res = PoesiaController.salvaCommento(commentoDTO, testPoesiaId);
        assertTrue(res);
    }

    @Test
    public void testHasUserCuorePoesiaAndToggleCuore() {
        int userId = user.getId();
        boolean liked = PoesiaController.hasUserCuorePoesia(testPoesiaId, userId);
        boolean toggled = PoesiaController.toggleCuore(testPoesiaId, userId);
        assertTrue(toggled);
        boolean likedAfter = PoesiaController.hasUserCuorePoesia(testPoesiaId, userId);
        assertNotEquals(liked, likedAfter);
    }
}