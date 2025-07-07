package boundary;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import controller.PiattaformaController;
import controller.PoesiaController;


/**
 * Panel che visualizza le raccolte create dall'utente corrente.
 * Permette anche di accedere alle funzionalità di creazione di nuove raccolte
 * e di visualizzare tutte le raccolte dell'utente.
 */
public class MieRaccoltePanel extends JPanel {

    private PiattaformaController piattaformaController = PiattaformaController.getInstance();
    /**
     * Costruttore che crea e configura il pannello con le raccolte dell'utente.
     * Recupera le raccolte dal controller e le visualizza in un layout verticale.
     * Include pulsanti per creare nuove raccolte e visualizzare tutte le raccolte.
     *
     */
    public MieRaccoltePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titoloLabel = UIUtils.titolo("Le mie raccolte", 0, 0, 16);
        headerPanel.add(titoloLabel, BorderLayout.WEST);

        JButton addButton = UIUtils.bottone("+", Font.BOLD,14);
        addButton.addActionListener(_ -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            new RaccolteFrame(true).setVisible(true);
        });
        headerPanel.add(addButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        JPanel raccolteContainer = new JPanel();
        raccolteContainer.setLayout(new BoxLayout(raccolteContainer, BoxLayout.Y_AXIS));
        raccolteContainer.setBackground(Color.WHITE);

        try {
            List<entity.Raccolta> raccolte = PoesiaController.getRaccolteUtente(piattaformaController.getCurrentUser().getId());

            if (raccolte.isEmpty()) {
                JLabel noRaccolte = new JLabel("Non hai ancora creato raccolte");
                noRaccolte.setFont(new Font(UIUtils.FONT, Font.ITALIC, 14));
                noRaccolte.setAlignmentX(Component.CENTER_ALIGNMENT);
                raccolteContainer.add(Box.createVerticalStrut(20));
                raccolteContainer.add(noRaccolte);
            } else {
                for (entity.Raccolta raccolta : raccolte) {
                    JPanel raccoltePanel = new RaccoltaDisplayPanel(raccolta);
                    raccolteContainer.add(raccoltePanel);
                    raccolteContainer.add(Box.createVerticalStrut(15));
                }
            }
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Errore nel caricamento delle raccolte");
            errorLabel.setFont(new Font(UIUtils.FONT, Font.ITALIC, 14));
            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            raccolteContainer.add(errorLabel);
        }

        JScrollPane scrollPane = new JScrollPane(raccolteContainer);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        JButton vediTutteButton = UIUtils.bottone("Visualizza tutte", Font.PLAIN,14);
        vediTutteButton.addActionListener(_ -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            new RaccolteFrame(false).setVisible(true);
        });

        add(vediTutteButton, BorderLayout.SOUTH);
    }
}
