package boundary;

import DTO.RaccoltaDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Panel che visualizza i dettagli di una raccolta di poesie.
 * Mostra il titolo, la descrizione e fornisce un pulsante per visualizzare le poesie contenute.
 */
public class RaccoltaDisplayPanel extends JPanel {
    /**
     * Raccolta da visualizzare nel panel.
     */
    private final RaccoltaDTO raccolta;

    /**
     * Pulsante per visualizzare le poesie contenute nella raccolta.
     */
    private JButton viewButton;

    /**
     * Costruttore che crea un pannello per visualizzare i dettagli di una raccolta.
     *
     * @param raccolta Raccolta di poesie da visualizzare.
     */
    public RaccoltaDisplayPanel(RaccoltaDTO raccolta) {
        this.raccolta = raccolta;
        initialize();
    }

    /**
     * Inizializza e configura i componenti grafici del pannello.
     * Include il titolo, la descrizione e un pulsante per visualizzare le poesie.
     */
    private void initialize() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIUtils.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        setMaximumSize(new Dimension(UIUtils.CONTENT_WIDTH - 60, 120));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UIUtils.BORDER_SELECTED, 1),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UIUtils.BORDER_COLOR, 1),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            }
        });

        JLabel raccoltaTitolo = new JLabel(raccolta.getTitolo());
        raccoltaTitolo.setFont(new Font(UIUtils.FONT, Font.BOLD, 16));
        raccoltaTitolo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel raccoltaDesc = new JLabel(raccolta.getDescrizione() != null ?
                raccolta.getDescrizione() : "");
        raccoltaDesc.setFont(new Font(UIUtils.FONT, Font.ITALIC, 14));
        raccoltaDesc.setAlignmentX(Component.LEFT_ALIGNMENT);

        viewButton = UIUtils.bottone("Visualizza poesie", Font.PLAIN, 14);
        viewButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        viewButton.addActionListener(_ -> JOptionPane.showMessageDialog(this,
                "Funzionalità in sviluppo",
                "Info", JOptionPane.INFORMATION_MESSAGE));

        viewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                viewButton.setBackground(UIUtils.ACCENT_COLOR.darker());
                viewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                viewButton.setBackground(UIUtils.ACCENT_COLOR);
            }
        });

        add(raccoltaTitolo);
        add(Box.createVerticalStrut(5));
        add(raccoltaDesc);
        add(Box.createVerticalStrut(10));
        add(viewButton);
    }
}