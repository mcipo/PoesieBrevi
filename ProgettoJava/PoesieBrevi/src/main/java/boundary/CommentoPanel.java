package boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import controller.PiattaformaController;
import controller.PoesiaController;
import controller.ProfiloController;
import entity.Commento;

/**
 * Panel che gestisce la visualizzazione e l'inserimento dei commenti per una poesia.
 * Permette di visualizzare la lista dei commenti esistenti e di aggiungere nuovi commenti.
 */
public class CommentoPanel extends JPanel {

    private PiattaformaController piattaformaController = PiattaformaController.getInstance();

    /**
     * ID della poesia che l'utente sta commentando.
     */
    private final int poesiaId;
    
    /**
     * Panel contenente la lista dei commenti.
     */
    private final JPanel commentiListPanel;
    
    /**
     * ScrollPane che contiene la lista dei commenti per permettere lo scorrimento.
     */
    private final JScrollPane scrollPane;
    


    /**
     * Costruttore che inizializza il pannello dei commenti per una specifica poesia.
     *
     * @param poesiaId ID della poesia da commentare.
     */
    public CommentoPanel(int poesiaId) {
        this.poesiaId = poesiaId;


        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, UIUtils.BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        setBackground(new Color(250, 250, 250));

        JLabel titoloLabel = new JLabel("Commenti");
        titoloLabel.setFont(new Font(UIUtils.FONT, Font.BOLD, 14));
        add(titoloLabel, BorderLayout.NORTH);

        commentiListPanel = new JPanel();
        commentiListPanel.setLayout(new BoxLayout(commentiListPanel, BoxLayout.Y_AXIS));
        commentiListPanel.setBackground(new Color(250, 250, 250));

        scrollPane = new JScrollPane(commentiListPanel);
        scrollPane.setPreferredSize(new Dimension(0, 150));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        add(aggiungiCommentoPanel(), BorderLayout.SOUTH);

        caricaCommenti();
    }

    /**
     * Carica i commenti esistenti dal database e li mostra nell'interfaccia.
     * Per ogni commento viene creato un pannello apposito.
     * Mostra un messaggio se non ci sono commenti.
     */
    private void caricaCommenti() {
        try {
            List<Commento> commenti = PoesiaController.getCommenti(poesiaId);

            commentiListPanel.removeAll();

            if (commenti.isEmpty()) {
                JLabel noCommentiLabel = new JLabel("Nessun commento. Sii il primo a commentare!");
                noCommentiLabel.setFont(new Font(UIUtils.FONT, Font.ITALIC, 12));
                noCommentiLabel.setForeground(new Color(150, 150, 150));
                noCommentiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                commentiListPanel.add(noCommentiLabel);
            } else {
                for (Commento commento : commenti) {
                    JPanel commentPanel = creaPanelSingoloCommento(commento);
                    commentiListPanel.add(commentPanel);
                    commentiListPanel.add(Box.createVerticalStrut(8));
                }
            }
            commentiListPanel.revalidate();
            commentiListPanel.repaint();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Errore nel caricamento dei commenti: " + e.getMessage(),
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Crea e configura il pannello per l'inserimento di nuovi commenti.
     * Include un campo di testo per il commento e un pulsante per inviarlo.
     *
     * @return JPanel configurato per l'inserimento di commenti.
     */
    private JPanel aggiungiCommentoPanel() {
        JPanel addCommentPanel = new JPanel(new BorderLayout());
        addCommentPanel.setOpaque(false);
        addCommentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JTextField commentField = UIUtils.textField("Scrivi il tuo commento", 0, 0);

        JButton inviaButton = UIUtils.bottone("Invia", Font.BOLD, 12);
        inviaButton.setPreferredSize(new Dimension(80, 30));

        inviaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                inviaButton.setBackground(UIUtils.ACCENT_COLOR.darker());
                inviaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                inviaButton.setBackground(UIUtils.ACCENT_COLOR);
            }
        });

        inviaButton.addActionListener(_ -> {
            String contenuto = commentField.getText().trim();
            if (!contenuto.isEmpty()) {
                Commento nuovoCommento = new Commento(0, poesiaId, piattaformaController.getCurrentUser().getId(), contenuto, new java.util.Date());
                boolean success = PoesiaController.salvaCommento(nuovoCommento);

                if (success) {
                    JPanel newCommentPanel = creaPanelSingoloCommento(nuovoCommento);

                    if (commentiListPanel.getComponentCount() == 1 &&
                            commentiListPanel.getComponent(0) instanceof JLabel &&
                            ((JLabel) commentiListPanel.getComponent(0)).getText().startsWith("Nessun commento")) {
                        commentiListPanel.removeAll();
                    }

                    commentiListPanel.add(newCommentPanel);
                    commentiListPanel.add(Box.createVerticalStrut(8));

                    commentField.setText("");

                    commentiListPanel.revalidate();
                    commentiListPanel.repaint();

                    scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                }
            }
        });

        addCommentPanel.add(commentField, BorderLayout.CENTER);
        addCommentPanel.add(inviaButton, BorderLayout.EAST);

        return addCommentPanel;
    }

    /**
     * Crea un pannello che rappresenta un singolo commento nell'interfaccia.
     * Il pannello include il nome dell'utente, il testo del commento e la data di creazione.
     *
     * @param commento L'oggetto Commento da visualizzare.
     * @return JPanel configurato per visualizzare il commento.
     */
    private JPanel creaPanelSingoloCommento(Commento commento) {
        JPanel panel = new JPanel(new BorderLayout(5, 2));
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(240, 240, 240)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        String username = getUsernameById(commento.getAutoreID());
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font(UIUtils.FONT, Font.BOLD, 12));
        panel.add(usernameLabel, BorderLayout.NORTH);
        JLabel commentTextLabel = new JLabel( commento.getTesto());
        commentTextLabel.setFont(new Font(UIUtils.FONT, Font.PLAIN, 12));
        panel.add(commentTextLabel, BorderLayout.CENTER);

        JLabel dateLabel = new JLabel(UIUtils.formatDateCompact(commento.getDataCreazione()));
        dateLabel.setFont(new Font(UIUtils.FONT, Font.ITALIC, 10));
        dateLabel.setForeground(new Color(150, 150, 150));
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(dateLabel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Recupera lo username dell'utente dato il suo ID.
     *
     * @param userId ID dell'utente di cui recuperare lo username.
     * @return Username dell'utente, o "UTENTEDEFAULT" in caso di errore.
     */
    private String getUsernameById(int userId) {
        try {

            return ProfiloController.getUsernameByUserId(userId);

        } catch (Exception e) {
            return "UTENTEDEFAULT";
        }
    }
}