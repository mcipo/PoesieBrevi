package boundary;

import DTO.PoesiaDTO;
import DTO.RaccoltaDTO;
import controller.PiattaformaController;
import controller.RaccoltaController;
import controller.PoesiaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PoesieFrame extends JFrame {

    private final PiattaformaController piattaformaController = PiattaformaController.getInstance();
    
    private JPanel contentPanel;

    private JPanel mainPanel;

    private boolean createMode;

    public PoesieFrame(boolean createMode) {
        this.createMode = createMode;

        setTitle(createMode ? "Crea Nuova Poesia" : "Le Mie Poesie");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 700));

        mainPanel = UIUtils.setupMainPanel(getWidth(), getHeight());
        contentPanel = UIUtils.setupContentPanel(mainPanel, getWidth(), getHeight());
        setupHeaderPanel();
        
        if (createMode) {
            setupCreatePoesiaPanel();
        } else {
            setupPoesiaViewPanel();
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

        JLabel titleLabel = UIUtils.titolo(createMode ? "Crea Nuova Poesia" : "Le Mie Poesie", 0, 0);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton backButton = UIUtils.bottone("Torna alla Home", Font.PLAIN, 14);
        backButton.addActionListener(_ -> {
            dispose();
            new HomeFrame().setVisible(true);
        });
        headerPanel.add(backButton, BorderLayout.EAST);

        contentPanel.add(headerPanel);
    }

    private void setupCreatePoesiaPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 80, UIUtils.CONTENT_WIDTH - 40, UIUtils.CONTENT_HEIGHT - 100);

        int labelWidth = 100;
        int fieldWidth = UIUtils.CONTENT_WIDTH - 40 - labelWidth - 30;
        int y = 20;
        int height = 30;

        JLabel titleLabel = UIUtils.label("Titolo: ", 10, y, 14, labelWidth, height);
        titleLabel.setToolTipText("Inserisci il titolo della nuova poesia");
        panel.add(titleLabel);

        JTextField titleField = UIUtils.CreaPoesiaTextField(labelWidth + 10, y, fieldWidth, height);
        panel.add(titleField);

        y += height + 10; //60

        JLabel contentLabel = UIUtils.label("Contenuto: ", 10, y, 14, labelWidth, height);
        panel.add(contentLabel);

        JTextArea contentArea = new JTextArea();
        contentArea.setFont(new Font(UIUtils.FONT, Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIUtils.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JScrollPane contentScroll = new JScrollPane(contentArea);
        contentScroll.setBounds(labelWidth + 10, y, fieldWidth, 150);
        contentScroll.setBorder(null);
        panel.add(contentScroll);

        y += 150 + 20; //130

        JLabel tagsLabel = UIUtils.label("Tags: ", 10, y, 14, labelWidth, height);
        panel.add(tagsLabel);

        JTextField tagsField = UIUtils.CreaPoesiaTextField(labelWidth+10, y, fieldWidth, height);
        tagsField.setToolTipText("Inserisci i tag separati da virgola (es. amore, natura, vita)");
        panel.add(tagsField);

        y += height + 20; //150

        JLabel rcccoltaLabel = UIUtils.label("Raccolta: ", 10, y, 14);
        panel.add(rcccoltaLabel);


        JComboBox<String> raccoltaCombo = new JComboBox<>();
        raccoltaCombo.addItem("-- Nessuna Raccolta --");
        raccoltaCombo.addItem("-- Crea nuova raccolta --");

        try {
            List<RaccoltaDTO> raccolte = RaccoltaController.getRaccolteUtente(piattaformaController.getCurrentUser().getId());
            for (int i = 0; i < raccolte.size(); i++) {
                raccoltaCombo.addItem((i + 1) + " : " + raccolte.get(i).getTitolo());
            }
        } catch (Exception e) {

        }

        raccoltaCombo.setBounds(labelWidth + 10, y, fieldWidth, height);
        panel.add(raccoltaCombo);

        y += height + 20; //170

        JPanel newRaccoltaPanel = nuovaRaccoltaPanel(fieldWidth);
        newRaccoltaPanel.setBounds(10, y, UIUtils.CONTENT_WIDTH - 70, 100);
        newRaccoltaPanel.setVisible(false);
        panel.add(newRaccoltaPanel);

        JTextField newRaccoltaTitleField = (JTextField) findComponentByName(newRaccoltaPanel, "nuovaRaccoltaTitle");
        JTextField newRaccoltanDescField = (JTextField) findComponentByName(newRaccoltaPanel, "nuovaRaccoltaDesc");

        JCheckBox visibleCheckbox = new JCheckBox("Poesia pubblica");
        visibleCheckbox.setSelected(true);
        visibleCheckbox.setOpaque(false);
        panel.add(visibleCheckbox);

        y += height + 20;

        JButton pubblicaButton = UIUtils.bottone("Pubblica Poesia", Font.PLAIN, 14);
        panel.add(pubblicaButton);

        int finalY = y;

        Runnable updateYPosition = () ->{
            int yAttuale = finalY;
            if (newRaccoltaPanel.isVisible()) {
                yAttuale += 60;
            }
            visibleCheckbox.setBounds(labelWidth + 10, yAttuale, fieldWidth, height);
            yAttuale += 20;
            pubblicaButton.setBounds((UIUtils.CONTENT_WIDTH - 150) / 2, yAttuale, 150, 40);
            panel.revalidate();
            panel.repaint();
        };

        updateYPosition.run();

        raccoltaCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedItem = (String) raccoltaCombo.getSelectedItem();
                boolean shouldBeVisible = "-- Crea nuova raccolta --".equals(selectedItem);
                newRaccoltaPanel.setVisible(shouldBeVisible);
                updateYPosition.run();
            }
        });

        pubblicaButton.addActionListener(_ -> {
            if (raccoltaCombo.getSelectedItem().equals("-- Crea nuova raccolta --")) {
                JOptionPane.showMessageDialog(PoesieFrame.this,
                        "Inserire una raccolta!",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (PubblicaPoesia(titleField, contentArea, tagsField, visibleCheckbox,
                    raccoltaCombo, newRaccoltaTitleField, newRaccoltanDescField)) {
                resetPoesiaForm(titleField, contentArea, tagsField, raccoltaCombo, newRaccoltaPanel);
                dispose();
                new HomeFrame().setVisible(true);
            }
        });

        contentPanel.add(panel);
    }

    private JPanel nuovaRaccoltaPanel(int fieldWidth) {
        JPanel nuovaRaccoltaPanel = new JPanel(null);
        nuovaRaccoltaPanel.setBackground(Color.WHITE);
        nuovaRaccoltaPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(UIUtils.BORDER_COLOR), "Nuova Raccolta"));

        int height = 30;


        JLabel nuovaRaccoltaTitleLabel = UIUtils.label("Titolo Raccolta: ", 10, 20, 14);
        nuovaRaccoltaTitleLabel.setBounds(10, 20, 100, height);
        nuovaRaccoltaPanel.add(nuovaRaccoltaTitleLabel);

        JTextField nuovaRaccoltaTitleField = UIUtils.textField("", 0, 0);
        nuovaRaccoltaTitleField.setName("nuovaRaccoltaTitle");
        nuovaRaccoltaTitleField.setBounds(150, 20, fieldWidth - 100, height);
        nuovaRaccoltaPanel.add(nuovaRaccoltaTitleField);

        JLabel nuovaRaccoltaDescLabel = UIUtils.label("Descrizione: ", 10, 20 + height + 10, 14);
        nuovaRaccoltaDescLabel.setBounds(10, 20 + height + 10, 100, height);
        nuovaRaccoltaPanel.add(nuovaRaccoltaDescLabel);

        JTextField nuovaRaccoltaDescField = UIUtils.textField("", 0, 0);
        nuovaRaccoltaDescField.setName("nuovaRaccoltaDesc");
        nuovaRaccoltaDescField.setBounds(150, 20 + height + 10, fieldWidth - 100, height);
        nuovaRaccoltaPanel.add(nuovaRaccoltaDescField);

        return nuovaRaccoltaPanel;
    }

    private Component findComponentByName(Container container, String name) {
        for (Component component : container.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            }
        }
        return null;
    }

    private boolean PubblicaPoesia(JTextField titleField, JTextArea contentArea,
                                  JTextField tagsField, JCheckBox visibleCheckbox, JComboBox<String> RaccoltaCombo,
                                  JTextField newRaccoltaTitleField, JTextField newRaccoltaDescField) {
        String titolo = titleField.getText().trim();
        String contenuto = contentArea.getText().trim();
        String tags = tagsField.getText().trim();
        boolean isVisible = visibleCheckbox.isSelected();

        if (titolo.isEmpty() || contenuto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Titolo e contenuto sono obbligatori",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (contenuto.length() > 500) {
            JOptionPane.showMessageDialog(this,
                    "Il contenuto non può superare i 500 caratteri",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        int raccoltaId = -1; // -1 nessuna raccolta selezionata
        String raccoltaSelezionata = (String) RaccoltaCombo.getSelectedItem();

        try {


            if (raccoltaSelezionata.equals("-- Crea nuova raccolta --")) {

                String nuovoTitolo = newRaccoltaTitleField.getText().trim();
                String nuovaDescrizione = newRaccoltaDescField.getText().trim();

                if (nuovoTitolo.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Il titolo della raccolta è obbligatorio",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                raccoltaId = RaccoltaController.creaRaccolta(nuovoTitolo, nuovaDescrizione, piattaformaController.getCurrentUser().getId());

                if (raccoltaId == -1) {
                    JOptionPane.showMessageDialog(this,
                            "Errore durante la creazione della raccolta",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else if (!raccoltaSelezionata.equals("-- Nessuna Raccolta --")) {
                raccoltaId = Integer.parseInt(raccoltaSelezionata.split(":")[0].trim());
            }

            boolean success = PoesiaController.creaPoesia(titolo, contenuto, tags, isVisible,
                    piattaformaController.getCurrentUser().getId(), raccoltaId);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Poesia pubblicata con successo!",
                        "Successo", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(this,
                        "Errore durante la pubblicazione della poesia",
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

    private void resetPoesiaForm(JTextField titleField, JTextArea contentArea,
                               JTextField tagsField, JComboBox<String> raccoltaCombo, JPanel newRaccoltaPanel) {
        titleField.setText("");
        contentArea.setText("");
        tagsField.setText("");
        raccoltaCombo.setSelectedIndex(0);
        newRaccoltaPanel.setVisible(false);
    }

    private void setupPoesiaViewPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 80, UIUtils.CONTENT_WIDTH - 40, UIUtils.CONTENT_HEIGHT - 100);

        JButton nuovaPoesiaButton = new JButton("Crea Nuova Poesia");
        nuovaPoesiaButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        nuovaPoesiaButton.setBackground(UIUtils.BUTTON_COLOR);
        nuovaPoesiaButton.setForeground(Color.BLACK);
        nuovaPoesiaButton.setFocusPainted(false);
        nuovaPoesiaButton.setMargin(new Insets(8, 15, 8, 15));
        nuovaPoesiaButton.addActionListener(_ -> {
            dispose();
            new PoesieFrame(true).setVisible(true);
        });

        panel.add(nuovaPoesiaButton, BorderLayout.SOUTH);

        JPanel poesieContainer = new JPanel();
        poesieContainer.setLayout(new BoxLayout(poesieContainer, BoxLayout.Y_AXIS));
        poesieContainer.setBackground(Color.WHITE);

        poesieContainer.add(Box.createVerticalStrut(20));

        try {

            List<PoesiaDTO> poesie = PoesiaController.getPoesieByAutore(piattaformaController.getCurrentUser().getId());

            if (poesie.isEmpty()) {
                JLabel nessunaPoesia = new JLabel("Non hai ancora scritto poesie");
                nessunaPoesia.setFont(new Font(UIUtils.FONT, Font.ITALIC, 14));
                nessunaPoesia.setAlignmentX(Component.CENTER_ALIGNMENT);
                poesieContainer.add(Box.createVerticalStrut(20));
                poesieContainer.add(nessunaPoesia);
            } else {
                for (PoesiaDTO poesia : poesie) {
                    PoesiaDisplayPanel poesiaPanel = new PoesiaDisplayPanel(poesia);
                    poesieContainer.add(poesiaPanel);
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
        panel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(panel);
    }
}