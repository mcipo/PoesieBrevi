package boundary;


import controller.PiattaformaController;
import controller.RaccoltaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import DTO.RaccoltaDTO;

public class RaccolteFrame extends JFrame {

    private PiattaformaController piattaformaController = PiattaformaController.getInstance();

    private JPanel contentPanel, mainPanel;
    
    private boolean createMode;

    public RaccolteFrame(boolean createMode) {
        this.createMode = createMode;

        setTitle(createMode ? "Crea Nuova Raccolta" : "Le Mie Raccolte");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 700));

        mainPanel = UIUtils.setupMainPanel(getWidth(), getHeight());
        contentPanel = UIUtils.setupContentPanel(mainPanel, getWidth(), getHeight());
        setupHeaderPanel();
        
        if (createMode) {
            setupCreaRaccoltaPanel();
        } else {
            setupRaccoltaViewPanel();
        }

        add(mainPanel);
        UIUtils.centerContentPanel(getWidth(), getHeight(), contentPanel);
        repaint();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                UIUtils.centerContentPanel(getWidth(), getHeight(), contentPanel);
                repaint();
            }
        });
    }

    private void setupHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBounds(20, 20, UIUtils.CONTENT_WIDTH - 40, 50);

        JLabel titleLabel = UIUtils.titolo(createMode ? "Crea Nuova Raccolta" : "Le Mie Raccolte", 0,0, 18);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton backButton = UIUtils.bottone("Torna alla Home", Font.PLAIN, 14);
        backButton.addActionListener(_ -> {
            dispose();
            new HomeFrame().setVisible(true);
        });
        headerPanel.add(backButton, BorderLayout.EAST);

        contentPanel.add(headerPanel);
    }

    private void setupCreaRaccoltaPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 80, UIUtils.CONTENT_WIDTH - 40, UIUtils.CONTENT_HEIGHT - 100);

        int labelWidth = 100;
        int fieldWidth = UIUtils.CONTENT_WIDTH - 40 - labelWidth - 30;
        int y = 20;
        int height = 30;

        JLabel titleLabel = UIUtils.label("Titolo:", 10, y, 14, labelWidth, height);
        panel.add(titleLabel);

        JTextField titleField = UIUtils.CreaPoesiaTextField(labelWidth + 10, y, fieldWidth, height);
        panel.add(titleField);

        y += height + 20;

        JLabel descriptionLabel = UIUtils.label("Descrizione:", 10, y, 14, labelWidth, height);
        panel.add(descriptionLabel);

        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setFont(new Font(UIUtils.FONT, Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIUtils.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        descriptionScroll.setBounds(labelWidth + 10, y, fieldWidth, 100);
        descriptionScroll.setBorder(null);
        panel.add(descriptionScroll);

        y += 100 + 40;


        JButton creaRaccolta = UIUtils.bottone("Crea Raccolta", Font.PLAIN, 14);
        creaRaccolta.setBounds((UIUtils.CONTENT_WIDTH - 150) / 2, y, 150, 40);
        panel.add(creaRaccolta);

        creaRaccolta.addActionListener(_ -> {
            if (handleCollectionCreation(titleField, descriptionArea)) {
                dispose();
                new HomeFrame().setVisible(true);
            }
        });

        contentPanel.add(panel);
    }

    private boolean handleCollectionCreation(JTextField titleField, JTextArea descriptionArea) {
        String titolo = titleField.getText().trim();
        String descrizione = descriptionArea.getText().trim();

        if (titolo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Il titolo è obbligatorio",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {

            int raccoltaId = RaccoltaController.creaRaccolta(titolo, descrizione, piattaformaController.getCurrentUser().getId());

            if (raccoltaId != -1) {
                JOptionPane.showMessageDialog(this,
                        "Raccolta creata con successo!",
                        "Successo", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(this,
                        "Errore durante la creazione della raccolta",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Errore durante l'operazione: " + ex.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void setupRaccoltaViewPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 80, UIUtils.CONTENT_WIDTH - 40, UIUtils.CONTENT_HEIGHT - 100);

        JLabel titleLabel = new JLabel("Le mie raccolte");
        titleLabel.setFont(new Font(UIUtils.FONT, Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);


        JPanel collectionsContainer = new JPanel();
        collectionsContainer.setLayout(new BoxLayout(collectionsContainer, BoxLayout.Y_AXIS));
        collectionsContainer.setBackground(Color.WHITE);


        JButton createNewButton = UIUtils.bottone("Crea Nuova Raccolta", Font.PLAIN, 14);
        createNewButton.addActionListener(_ -> {
            dispose();
            new RaccolteFrame(true).setVisible(true);
        });
        
        panel.add(createNewButton, BorderLayout.SOUTH );
        collectionsContainer.add(Box.createVerticalStrut(20));

        try {

            List<RaccoltaDTO> raccolte = RaccoltaController.getRaccolteUtente(piattaformaController.getCurrentUser().getId());

            if (raccolte.isEmpty()) {
                JLabel noCollections = new JLabel("Non hai ancora creato raccolte");
                noCollections.setFont(new Font(UIUtils.FONT, Font.ITALIC, 14));
                noCollections.setAlignmentX(Component.CENTER_ALIGNMENT);
                collectionsContainer.add(Box.createVerticalStrut(20));
                collectionsContainer.add(noCollections);
            } else {
                for (RaccoltaDTO raccolta : raccolte) {
                    RaccoltaDisplayPanel collectionPanel = new RaccoltaDisplayPanel(raccolta);
                    collectionsContainer.add(collectionPanel);
                    collectionsContainer.add(Box.createVerticalStrut(15));
                }
            }
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Errore nel caricamento delle raccolte");
            errorLabel.setFont(new Font(UIUtils.FONT, Font.ITALIC, 14));
            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            collectionsContainer.add(errorLabel);
        }

        JScrollPane scrollPane = new JScrollPane(collectionsContainer);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(panel);
    }
}
