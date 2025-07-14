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
import DTO.CommentoDTO;

public class CommentoPanel extends JPanel {

    private PiattaformaController piattaformaController = PiattaformaController.getInstance();

    private final int poesiaId;
    
    private final JPanel commentiListPanel;
    
    private final JScrollPane scrollPane;
    
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

    private void caricaCommenti() {
        try {
            List<CommentoDTO> commenti = PoesiaController.getCommenti(poesiaId);

            commentiListPanel.removeAll();

            if (commenti.isEmpty()) {
                JLabel noCommentiLabel = new JLabel("Nessun commento. Sii il primo a commentare!");
                noCommentiLabel.setFont(new Font(UIUtils.FONT, Font.ITALIC, 12));
                noCommentiLabel.setForeground(new Color(150, 150, 150));
                noCommentiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                commentiListPanel.add(noCommentiLabel);
            } else {
                for (CommentoDTO commento : commenti) {
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
                String username = ProfiloController.getUsernameByUserId(piattaformaController.getCurrentUser().getId());
                CommentoDTO nuovoCommento = new CommentoDTO(username, contenuto, new java.util.Date());
                boolean success = PoesiaController.salvaCommento(nuovoCommento, poesiaId);

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

    private JPanel creaPanelSingoloCommento(CommentoDTO commento) {
        JPanel panel = new JPanel(new BorderLayout(5, 2));
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(240, 240, 240)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        String username = commento.getUsername();
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


}