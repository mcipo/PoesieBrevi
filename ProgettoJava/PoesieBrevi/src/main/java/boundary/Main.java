package boundary;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Classe principale che avvia l'applicazione "Poesie Brevi".
 * Inizializza l'interfaccia utente e mostra la schermata di login.
 */
public class Main {
    /**
     * Metodo main che rappresenta il punto di ingresso dell'applicazione.
     * Configura il look and feel di sistema e avvia l'applicazione nel thread di gestione eventi Swing.
     *
     * @param args Argomenti della riga di comando (non utilizzati).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginFrame().setVisible(true);
        });
    }
}

