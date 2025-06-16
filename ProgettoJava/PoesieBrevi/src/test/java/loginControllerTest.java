import controller.LoginController;
import entity.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class loginControllerTest {

    private final String testEmail = "test@prova.com";
    private final String correctPassword = "password123";
    private final String wrongPassword = "passwordSbagliata";
    private final String nonExistentEmail = "emailSbagliata@email.com";

    @Before
    public void setUp() {

    }

    @Test
    public void testEsisteUtente() {
        boolean exists = LoginController.esisteUtente(testEmail);
        assertTrue("L'utente dovrebbe esistere nel database", exists);
        System.out.println("Test EsisteUtente - utente esistente");
        boolean notExists = LoginController.esisteUtente(nonExistentEmail);
        assertFalse("L'utente non dovrebbe esistere nel database", notExists);
        System.out.println("Test EsisteUtente - utente non esistente");
    }

    @Test
    public void testVerificaCredenziali() {
        boolean validCredentials = LoginController.verificaCredenziali(testEmail, correctPassword);
        assertTrue("Le credenziali corrette dovrebbero essere valide", validCredentials);

        boolean invalidCredentials = LoginController.verificaCredenziali(testEmail, wrongPassword);
        assertFalse("Le credenziali con password errata dovrebbero essere invalide", invalidCredentials);

        boolean nonExistentCredentials = LoginController.verificaCredenziali(nonExistentEmail, correctPassword);
        assertFalse("Le credenziali con email inesistente dovrebbero essere invalide", nonExistentCredentials);
    }

    @Test
    public void testAutenticaUtente() {

        User authenticatedUser = LoginController.autenticaUtente(testEmail, correctPassword);
        assertNotNull("L'autenticazione dovrebbe riuscire con credenziali corrette", authenticatedUser);
        assertEquals("L'email dell'utente autenticato dovrebbe corrispondere", testEmail, authenticatedUser.getEmail());

        User invalidAuth = LoginController.autenticaUtente(testEmail, wrongPassword);
        assertNull("L'autenticazione dovrebbe fallire con password errata", invalidAuth);

        User nonExistentAuth = LoginController.autenticaUtente(nonExistentEmail, correctPassword);
        assertNull("L'autenticazione dovrebbe fallire con email inesistente", nonExistentAuth);
    }

    @Test
    public void testIsAdmin() {

        boolean isAdmin = LoginController.isAdmin(testEmail, correctPassword);
        assertFalse("L'utente non dovrebbe essere un amministratore", isAdmin);

        boolean invalidAdmin = LoginController.isAdmin(testEmail, wrongPassword);
        assertFalse("L'autenticazione dovrebbe fallire con password errata", invalidAdmin);
    }

}
