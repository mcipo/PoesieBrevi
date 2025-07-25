package boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import controller.PiattaformaController;
import controller.PoesiaController;
import controller.ProfiloController;
import DTO.PoesiaDTO;
import DTO.ProfiloDTO;

public class PoesiaDisplayPanel extends JPanel {

    private PiattaformaController piattaformaController = PiattaformaController.getInstance();

    private final PoesiaDTO poesia;
    
    private boolean commentiVisibili = false;
    
    private CommentoPanel commentiPanel = null;

    public PoesiaDisplayPanel(PoesiaDTO poesia) {
        this.poesia = poesia;

        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(UIUtils.CONTENT_WIDTH - 60, 1000));
        setBackground(Color.WHITE);

        JPanel poesiaPanel = creaMainPoesiaPanel();

        add(poesiaPanel, BorderLayout.CENTER);
    }

    private JPanel creaMainPoesiaPanel() {
        JPanel poesiaPanel = new JPanel();
        poesiaPanel.setLayout(new BorderLayout(0, 5));
        poesiaPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIUtils.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)));
        poesiaPanel.setBackground(Color.WHITE);

        poesiaPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                poesiaPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UIUtils.BORDER_SELECTED, 1),
                        BorderFactory.createEmptyBorder(12, 15, 12, 15)));
                poesiaPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                poesiaPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UIUtils.BORDER_COLOR, 1),
                        BorderFactory.createEmptyBorder(12, 15, 12, 15)));
            }
        });

        poesiaPanel.add(creaHeaderPanel(), BorderLayout.NORTH);

        poesiaPanel.add(createContentPanel(), BorderLayout.CENTER);

        poesiaPanel.add(createFooterPanel(), BorderLayout.SOUTH);

        return poesiaPanel;
    }

    private JPanel creaHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = UIUtils.titolo(poesia.getTitolo(), 0, 0, 16);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        ProfiloDTO autore = null;
        try {

            autore = ProfiloController.caricaProfilo(poesia.getAutoreID());

        } catch (Exception e) {

        }
        JPanel autorePanel = new JPanel();
        autorePanel.setOpaque(false);
        autorePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));

        JLabel autoreLabel = new JLabel("di " + autore.getUsername());
        autoreLabel.setFont(new Font(UIUtils.FONT, Font.ITALIC, 14));
        autorePanel.add(autoreLabel);

        ImageIcon originalIcon = new ImageIcon(autore.getImmagineProfilo());
        Image scaledImage = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        autorePanel.add(imageLabel);

        headerPanel.add(autorePanel, BorderLayout.EAST);
        return headerPanel;
    }

    private JScrollPane createContentPanel() {
        JTextArea contentArea = new JTextArea(poesia.getContenuto());
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBackground(new Color(255, 255, 255));
        contentArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIUtils.BORDER_COLOR),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        contentArea.setFont(new Font(UIUtils.FONT, Font.PLAIN, 14));

        JScrollPane contentScroll = new JScrollPane(contentArea);
        contentScroll.setBorder(null);

        return contentScroll;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setOpaque(false);

        Font smallFont = new Font(UIUtils.FONT, Font.ITALIC, 10);
        Color lightTextColor = new Color(150, 150, 150);

        if (poesia.getDataCreazione() != null) {
            String formattedDate = UIUtils.formatDateCompact(poesia.getDataCreazione());
            JLabel dataLabel = new JLabel(formattedDate);
            dataLabel.setFont(smallFont);
            dataLabel.setForeground(lightTextColor);
            infoPanel.add(dataLabel);
        }

        if (poesia.getTags() != null && !poesia.getTags().isEmpty()) {
            JLabel tagsLabel = new JLabel(" | Tags: " + String.join(", ", poesia.getTags()));
            tagsLabel.setFont(smallFont);
            tagsLabel.setForeground(lightTextColor);
            infoPanel.add(tagsLabel);
        }

        footerPanel.add(infoPanel, BorderLayout.WEST);
        footerPanel.add(createInteractionPanel(), BorderLayout.EAST);

        return footerPanel;
    }

    private JPanel createInteractionPanel() {
        JPanel interactionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        interactionPanel.setOpaque(false);

        try {
            JButton cuoreButton = getCuoreButton();

            JButton commentoButton = getCommentoButton();

            interactionPanel.add(cuoreButton);
            interactionPanel.add(commentoButton);
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Errore caricamento interazioni");
            errorLabel.setFont(new Font(UIUtils.FONT, Font.ITALIC, 12));
            interactionPanel.add(errorLabel);
        }

        return interactionPanel;
    }

    private JButton getCommentoButton() throws SQLException {

        int numCommenti = PoesiaController.getNumCommenti(poesia.getId());

        JButton commentButton = new JButton("\uDBC0\uDF24 Commenti (" + numCommenti + ")");
        commentButton.setFont(new Font(UIUtils.FONT, Font.BOLD, 14));
        commentButton.setBorderPainted(false);
        commentButton.setContentAreaFilled(false);
        commentButton.setFocusPainted(false);
        commentButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        commentButton.setForeground(UIUtils.TEXT_COLOR);
        commentButton.addActionListener(_ -> toggleCommentsPanel(commentButton, numCommenti));
        return commentButton;
    }

    private JButton getCuoreButton() {

        int numCuori = PoesiaController.getNumCuori(poesia.getId());
        boolean isCuore = PoesiaController.hasUserCuorePoesia(poesia.getId(), piattaformaController.getCurrentUser().getId());

        JButton cuoreButton = new JButton((isCuore ? "\uDBC0\uDEB5" : "\uDBC0\uDEB4") + numCuori);
        cuoreButton.setFont(new Font(UIUtils.FONT, Font.BOLD, 14));
        cuoreButton.setBorderPainted(false);
        cuoreButton.setContentAreaFilled(false);
        cuoreButton.setFocusPainted(false);
        cuoreButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cuoreButton.setForeground(isCuore ? Color.red : UIUtils.TEXT_COLOR);
        cuoreButton.addActionListener(_ -> handleCuoreButtonClick(cuoreButton, poesia.getId()));
        return cuoreButton;
    }

    private void toggleCommentsPanel(JButton commentButton, int numCommenti) {
        commentiVisibili = !commentiVisibili;
        if (commentiVisibili) {
            if (commentiPanel == null) {
                commentiPanel = new CommentoPanel(poesia.getId());
            }
            add(commentiPanel, BorderLayout.SOUTH);
        } else if (commentiPanel != null) {
            remove(commentiPanel);
        }

        commentButton.setText("\uDBC0\uDF24 " + (commentiVisibili ? "Nascondi" : "Commenti (" + numCommenti + ")"));

        revalidate();
        repaint();
    }

    private void handleCuoreButtonClick(JButton cuoreButton, int poesiaId) {
        try {

            boolean success = PoesiaController.toggleCuore(poesiaId, piattaformaController.getCurrentUser().getId());

            if (success) {
                int newNumCuori = PoesiaController.getNumCuori(poesiaId);
                boolean userHasCuore = PoesiaController.hasUserCuorePoesia(poesiaId, piattaformaController.getCurrentUser().getId());

                cuoreButton.setText((userHasCuore ? "\uDBC0\uDEB5" : "\uDBC0\uDEB4") + newNumCuori);
                cuoreButton.setForeground(userHasCuore ? Color.red : UIUtils.TEXT_COLOR);
            }
        } catch (Exception e) {

        }
    }

}