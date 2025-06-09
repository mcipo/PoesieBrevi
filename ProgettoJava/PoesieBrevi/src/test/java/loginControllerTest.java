import controller.LoginController;
import entity.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class loginControllerTest {

    private LoginController loginController;
    private final String testEmail = "m@email.com";//"emailCorretta@email.com"; //email esistente
    private final String correctPassword = "1234";
    private final String wrongPassword = "passwordSbagliata";
    private final String nonExistentEmail = "emailSbagliata@email.com";

    @Before
    public void setUp() {
        loginController = new LoginController();
    }

    @Test
    public void testEsisteUtente() {
        boolean exists = loginController.esisteUtente(testEmail);
        assertTrue("L'utente dovrebbe esistere nel database", exists);
        System.out.println("Test EsisteUtente - utente esistente");
        boolean notExists = loginController.esisteUtente(nonExistentEmail);
        assertFalse("L'utente non dovrebbe esistere nel database", notExists);
        System.out.println("Test EsisteUtente - utente non esistente");
    }

    @Test
    public void testVerificaCredenziali() {
        boolean validCredentials = loginController.verificaCredenziali(testEmail, correctPassword);
        assertTrue("Le credenziali corrette dovrebbero essere valide", validCredentials);

        boolean invalidCredentials = loginController.verificaCredenziali(testEmail, wrongPassword);
        assertFalse("Le credenziali con password errata dovrebbero essere invalide", invalidCredentials);

        boolean nonExistentCredentials = loginController.verificaCredenziali(nonExistentEmail, correctPassword);
        assertFalse("Le credenziali con email inesistente dovrebbero essere invalide", nonExistentCredentials);
    }

    @Test
    public void testAutenticaUtente() {

        User authenticatedUser = loginController.autenticaUtente(testEmail, correctPassword);
        assertNotNull("L'autenticazione dovrebbe riuscire con credenziali corrette", authenticatedUser);
        assertEquals("L'email dell'utente autenticato dovrebbe corrispondere", testEmail, authenticatedUser.getEmail());

        User invalidAuth = loginController.autenticaUtente(testEmail, wrongPassword);
        assertNull("L'autenticazione dovrebbe fallire con password errata", invalidAuth);

        User nonExistentAuth = loginController.autenticaUtente(nonExistentEmail, correctPassword);
        assertNull("L'autenticazione dovrebbe fallire con email inesistente", nonExistentAuth);
    }

    @Test
    public void testIsAdmin() {

        boolean isAdmin = loginController.isAdmin(testEmail, correctPassword);
        assertFalse("L'utente non dovrebbe essere un amministratore", isAdmin);

        boolean invalidAdmin = loginController.isAdmin(testEmail, wrongPassword);
        assertFalse("L'autenticazione dovrebbe fallire con password errata", invalidAdmin);
    }

}
