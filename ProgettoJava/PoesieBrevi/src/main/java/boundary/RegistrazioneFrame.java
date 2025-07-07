package boundary;

import controller.PiattaformaController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Frame che gestisce l'interfaccia di registrazione di nuovi utenti.
 * Permette l'inserimento dei dati personali, credenziali e l'opzione per registrarsi
 * come amministratore.
 */
public class RegistrazioneFrame extends JFrame {
    /**
     * Campo di testo per l'inserimento dell'email.
     */
    private final JTextField emailField;
    
    /**
     * Campo di testo per l'inserimento del nome.
     */
    private final JTextField nomeField;
    
    /**
     * Campo di testo per l'inserimento del cognome.
     */
    private final JTextField cognomeField;
    
    /**
     * Campo di testo protetto per l'inserimento della password.
     */
    private final JPasswordField passwordField;
    
    /**
     * Campo di testo protetto per la conferma della password.
     */
    private final JPasswordField confermaPasswordField;
    
    /**
     * Checkbox per la selezione del ruolo di amministratore.
     */
    private final JCheckBox adminCheckbox;
    
    /**
     * Pannello principale che contiene i componenti dell'interfaccia.
     */
    private final JPanel contentPanel;



    /**
     * Costruttore che crea e configura la schermata di registrazione.
     * Inizializza tutti i campi e i controlli per l'inserimento dei dati utente.
     */
    public RegistrazioneFrame() {


        setTitle("Poesie Brevi - Registrazione");
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

        JLabel titoloLabel = UIUtils.titolo("Registrazione", 0, 20);

        JLabel nomeLabel = UIUtils.label("Nome", 20, 70, 14);
        nomeField = UIUtils.textField("Inserisci il tuo nome", 20, 95);

        JLabel cognomeLabel = UIUtils.label("Cognome", 20, 135, 14);
        cognomeField = UIUtils.textField("Inserisci il tuo cognome", 20, 160);

        JLabel emailLabel = UIUtils.label("Email", 20, 200, 14);
        emailField = UIUtils.textField("Inserisci il tuo email", 20, 225);

        JLabel passwordLabel = UIUtils.label("Password", 20, 265, 14);
        passwordField = UIUtils.passwordField("Inserisci la tua password", 20, 290);

        JLabel confermaPasswordLabel = UIUtils.label("Confirm Password", 20, 330, 14);
        confermaPasswordField = UIUtils.passwordField("Conferma la tua password", 20, 355);

        adminCheckbox = new JCheckBox("Registrati come amministratore");
        adminCheckbox.setFont(new Font(UIUtils.FONT, Font.PLAIN, 14));
        adminCheckbox.setForeground(UIUtils.TEXT_COLOR);
        adminCheckbox.setBounds(20, 400, UIUtils.CONTENT_WIDTH - 40, 30);
        adminCheckbox.setOpaque(false);
        adminCheckbox.setFocusPainted(false);
        
        JButton registratiButton = new JButton("Registrati");
        registratiButton.setBounds(20, 445, UIUtils.CONTENT_WIDTH - 40, 40);
        registratiButton.setBorder(BorderFactory.createLineBorder(UIUtils.BORDER_COLOR, 1));
        registratiButton.setForeground(UIUtils.TEXT_COLOR);
        registratiButton.setFont(new Font(UIUtils.FONT, Font.BOLD, 14));
        registratiButton.setBackground(UIUtils.BUTTON_COLOR);

        JLabel loginLabel = new JLabel("Hai già un account? Accedi");
        loginLabel.setFont(new Font(UIUtils.FONT, Font.PLAIN, 13));
        loginLabel.setForeground(UIUtils.BUTTON_COLOR);
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setBounds(0, 500, UIUtils.CONTENT_WIDTH, 20);
        
        contentPanel.add(titoloLabel);
        contentPanel.add(nomeLabel);
        contentPanel.add(nomeField);
        contentPanel.add(cognomeLabel);
        contentPanel.add(cognomeField);
        contentPanel.add(emailLabel);
        contentPanel.add(emailField);
        contentPanel.add(passwordLabel);
        contentPanel.add(passwordField);
        contentPanel.add(confermaPasswordLabel);
        contentPanel.add(confermaPasswordField);
        contentPanel.add(adminCheckbox);
        contentPanel.add(registratiButton);
        contentPanel.add(loginLabel);
        
        mainPanel.add(contentPanel);
        
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
        
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confermaPasswordField.getPassword());
                boolean isAdmin = adminCheckbox.isSelected();
                
                if(nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(RegistrazioneFrame.this, 
                        "Tutti i campi sono obbligatori", 
                        "Errore", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if(!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(RegistrazioneFrame.this, 
                        "Le password non corrispondono", 
                        "Errore", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if(!UIUtils.validaEmail(email)) {
                    JOptionPane.showMessageDialog(RegistrazioneFrame.this, 
                        "Inserisci un indirizzo email valido", 
                        "Errore", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (PiattaformaController.esisteUtente(email)) {
                    JOptionPane.showMessageDialog(RegistrazioneFrame.this, 
                        "Email già registrata nel sistema", 
                        "Errore", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                entity.User nuovoUtente = PiattaformaController.creaUtenteInMemoria(nome, cognome, email, password, isAdmin);
                
                if(nuovoUtente != null) {
                    JOptionPane.showMessageDialog(RegistrazioneFrame.this,
                        "Registrazione iniziale completata! Ora compila il tuo profilo.",
                        "Successo",
                        JOptionPane.INFORMATION_MESSAGE);
                        
                    dispose();
                    new ProfiloFrame(nuovoUtente, true).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(RegistrazioneFrame.this,
                        "Errore durante la creazione dell'utente.",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
    }
}