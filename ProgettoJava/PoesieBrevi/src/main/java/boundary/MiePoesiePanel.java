package boundary;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import controller.PiattaformaController;
import controller.PoesiaController;
import entity.Poesia;

/**
 * Panel che visualizza le poesie create dall'utente corrente.
 * Permette anche di accedere alle funzionalità di creazione di nuove poesie
 * e di visualizzare tutte le poesie dell'utente.
 */
public class MiePoesiePanel extends JPanel {

    private PiattaformaController piattaformaController = PiattaformaController.getInstance();
    /**
     * Costruttore che crea e configura il pannello con le poesie dell'utente.
     * Recupera le poesie dal controller e le visualizza in un layout verticale.
     * Include pulsanti per creare nuove poesie e visualizzare tutte le poesie.
     *
     */
    public MiePoesiePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JButton addButton = UIUtils.bottone("+", Font.BOLD,14);
        addButton.addActionListener(_ -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            new PoesieFrame(piattaformaController.getCurrentUser(), true).setVisible(true);
        });
        headerPanel.add(addButton, BorderLayout.EAST);

        JLabel titoloLabel = UIUtils.titolo("Le mie poesie", 0, 0, 16);
        headerPanel.add(titoloLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        JPanel poesieContainer = new JPanel();
        poesieContainer.setLayout(new BoxLayout(poesieContainer, BoxLayout.Y_AXIS));
        poesieContainer.setBackground(Color.WHITE);

        try {

            List<Poesia> poesie = PoesiaController.getPoesieByAutore(piattaformaController.getCurrentUser().getId());

            if (poesie.isEmpty()) {
                JLabel noPoesie = UIUtils.label("Non hai ancora scritto poesie", 0, 0, 14);
                poesieContainer.add(Box.createVerticalStrut(20));
                poesieContainer.add(noPoesie);
            } else {
                for (Poesia poesia : poesie) {
                    PoesiaDisplayPanel poesiePanel = new PoesiaDisplayPanel(poesia);
                    poesieContainer.add(poesiePanel);
                    poesieContainer.add(Box.createVerticalStrut(15));
                }
            }
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Errore nel caricamento delle poesie");
            errorLabel.setFont(new Font(UIUtils.FONT, Font.ITALIC, 14));
            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            poesieContainer.add(errorLabel);
        }

        JScrollPane scrollPane = new JScrollPane(poesieContainer);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        JButton visualizzaTutte = UIUtils.bottone("Visualizza tutte", Font.PLAIN ,14);
        visualizzaTutte.addActionListener(_ -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            new PoesieFrame(piattaformaController.getCurrentUser(), false).setVisible(true);
        });

        add(visualizzaTutte, BorderLayout.SOUTH);
    }
}
