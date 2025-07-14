package boundary;

import controller.PiattaformaController;
import DTO.ProfiloDTO;

import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

import java.util.Date;

import controller.ProfiloController;

public class ProfiloFrame extends JFrame {

    private PiattaformaController piattaformaController = PiattaformaController.getInstance();

    private final JTextField usernameField;

    private final JTextArea bioArea;

    private final JLabel immagineProfiloLabel;

    private final JSpinner dataNascitaSpinner;

    private final JPanel contentPanel;

    private String pathImmagineSelezionata = "";

    public ProfiloFrame(boolean nuovoUtente) {


        setTitle("Poesie Brevi - Profilo");
        setSize(UIUtils.CONTENT_MARGIN_W, UIUtils.CONTENT_MARGIN_H);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(UIUtils.BACKGROUND_COLOR);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);

        contentPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(UIUtils.BORDER_COLOR);
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            }
        };
        contentPanel.setOpaque(false);
        contentPanel.setBounds(0, 0, UIUtils.CONTENT_WIDTH, UIUtils.CONTENT_HEIGHT);

        JLabel titoloLabel = UIUtils.titolo(nuovoUtente ? "Crea il tuo Profilo" : "Gestisci Profilo", 0, 20);

        immagineProfiloLabel = new JLabel();

        immagineProfiloLabel.setBounds((UIUtils.CONTENT_WIDTH - 120) / 2, 70, 120, 120);
        immagineProfiloLabel.setBorder(BorderFactory.createLineBorder(UIUtils.BORDER_COLOR));
        immagineProfiloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon placeholderIcona = getImageIcon();

        immagineProfiloLabel.setIcon(placeholderIcona);

        JButton caricaImmagineButton = UIUtils.bottone("Carica Immagine", Font.PLAIN, 14);
        caricaImmagineButton.setBounds((UIUtils.CONTENT_WIDTH - 150) / 2, 200, 150, 40);
        caricaImmagineButton.setBackground(Color.WHITE);

        JLabel usernameLabel = UIUtils.label("Username", 20, 240, 14, 100, 20);

        usernameField = new JTextField();
        usernameField.setBounds(20, 265, UIUtils.CONTENT_WIDTH - 40, 30);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIUtils.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));
        usernameField.setBackground(new Color(250, 250, 255));

        JLabel bioLabel = UIUtils.label("Bio", 20, 310, 14, 100, 20);

        bioArea = new JTextArea();
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        bioArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIUtils.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        bioArea.setBackground(new Color(250, 250, 255));

        JScrollPane bioScrollPane = new JScrollPane(bioArea);
        bioScrollPane.setBounds(20, 330, UIUtils.CONTENT_WIDTH - 40, 80);
        bioScrollPane.setBorder(null);

        JLabel dobLabel = UIUtils.label("Data di Nascita", 20, 420, 14, 150, 20);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        dataNascitaSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dataNascitaSpinner, "dd/MM/yyyy");
        dataNascitaSpinner.setEditor(dateEditor);
        dataNascitaSpinner.setBounds(20, 445, UIUtils.CONTENT_WIDTH - 40, 30);

        JButton saveButton = UIUtils.bottone(nuovoUtente ? "Crea Profilo" : "Salva Modifiche", Font.BOLD, 14);
        saveButton.setBounds(20, 490, UIUtils.CONTENT_WIDTH - 40, 40);
        saveButton.setBackground(UIUtils.BUTTON_COLOR);

        contentPanel.add(titoloLabel);
        contentPanel.add(immagineProfiloLabel);
        contentPanel.add(caricaImmagineButton);
        contentPanel.add(usernameLabel);
        contentPanel.add(usernameField);
        contentPanel.add(bioLabel);
        contentPanel.add(bioScrollPane);
        contentPanel.add(dobLabel);
        contentPanel.add(dataNascitaSpinner);
        contentPanel.add(saveButton);

        mainPanel.add(contentPanel);
        add(mainPanel);

        UIUtils.centerContentPanel(getWidth(), getHeight(), contentPanel);
        repaint();
        if (!nuovoUtente) {
            caricaDatiProfilo();
        }

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
            UIUtils.centerContentPanel(getWidth(), getHeight(), contentPanel);
            repaint();            
        }
        });

        caricaImmagineButton.addActionListener(_ -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Immagini", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(ProfiloFrame.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                pathImmagineSelezionata = selectedFile.getAbsolutePath();

                ImageIcon imageIcon = new ImageIcon(new ImageIcon(pathImmagineSelezionata)
                        .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                immagineProfiloLabel.setIcon(imageIcon);
            }
        });

        saveButton.addActionListener(_ -> {
            String username = usernameField.getText().trim();
            String bio = bioArea.getText().trim();
            Date dataNascita = (Date) dataNascitaSpinner.getValue();

            if (username.isEmpty() || bio.isEmpty() || dataNascita == null) {
                JOptionPane.showMessageDialog(ProfiloFrame.this,
                        "Per favore, inserisci tutti i campi.",
                        "Campo obbligatorio", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ProfiloDTO profilo = new ProfiloDTO(username, bio, pathImmagineSelezionata, dataNascita);


            boolean datiValidi = ProfiloController.validaDatiProfilo(username, bio, dataNascita);

            if (!datiValidi) {
                JOptionPane.showMessageDialog(ProfiloFrame.this,
                        """
                                I dati inseriti non sono validi. Assicurati che:
                                - Username sia lungo tra 3 e 30 caratteri
                                - Bio non sia vuota e non superi i 500 caratteri
                                - La data di nascita sia valida (età minima 13 anni)""",
                        "Dati non validi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            piattaformaController.getCurrentUser().setProfilo(profilo);

            boolean success;

            if (nuovoUtente) {

                success = PiattaformaController.salvaUtente(piattaformaController.getCurrentUser());

                if (success) {
                    JOptionPane.showMessageDialog(ProfiloFrame.this,
                            "Account e profilo creati con successo!",
                            "Successo", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new LoginFrame().setVisible(true); // Ritorna alla schermata di login
                } else {
                    JOptionPane.showMessageDialog(ProfiloFrame.this,
                            "Errore durante la creazione dell'account. L'email potrebbe essere già in uso.",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                success = ProfiloController.salvaModificheProfilo(piattaformaController.getCurrentUser(), profilo);

                if (success) {
                    JOptionPane.showMessageDialog(ProfiloFrame.this,
                            "Profilo aggiornato con successo!",
                            "Successo", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    if (piattaformaController.getCurrentUser().isAdmin()) {
                        new StatisticheFrame().setVisible(true);
                    }else{
                        new HomeFrame().setVisible(true);
                    }

                } else {
                    JOptionPane.showMessageDialog(ProfiloFrame.this,
                            "Errore durante il salvataggio del profilo.",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private static ImageIcon getImageIcon() {
        ImageIcon placeholderIcona;
        BufferedImage placeholderImagine = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = placeholderImagine.createGraphics();
        g2d.setColor(new Color(200, 200, 200));
        g2d.fillRect(0, 0, 100, 100);
        g2d.setColor(Color.GRAY);
        g2d.drawRect(0, 0, 99, 99);
        g2d.drawLine(0, 0, 99, 99);
        g2d.drawLine(0, 99, 99, 0);
        g2d.dispose();
        placeholderIcona = new ImageIcon(placeholderImagine);
        return placeholderIcona;
    }

    private void caricaDatiProfilo() {
        try {

            ProfiloDTO profilo = ProfiloController.caricaProfilo(piattaformaController.getCurrentUser());
            
            if (profilo != null) {
                usernameField.setText(profilo.getUsername());
                bioArea.setText(profilo.getBio());
                
                if (profilo.getDataNascita() != null) {
                    dataNascitaSpinner.setValue(profilo.getDataNascita());
                } else {
                    dataNascitaSpinner.setValue(new Date());
                }

                String imgPath = profilo.getImmagineProfilo();
                if (imgPath != null && !imgPath.isEmpty()) {
                    File imgFile = new File(imgPath);
                    if (imgFile.exists()) {
                        pathImmagineSelezionata = imgPath;
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imgPath)
                                .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                        immagineProfiloLabel.setIcon(imageIcon);
                    }
                }
            } else {
                usernameField.setText("");
                bioArea.setText("");
                dataNascitaSpinner.setValue(new Date());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento del profilo: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}