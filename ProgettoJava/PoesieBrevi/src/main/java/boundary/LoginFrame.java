package boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import controller.PiattaformaController;

/**
 * Frame che gestisce l'interfaccia di login dell'applicazione.
 * Permette agli utenti di autenticarsi inserendo email e password,
 * con opzioni per il recupero password e la registrazione di nuovi account.
 */
public class LoginFrame extends JFrame {

    private PiattaformaController piattaformaController = PiattaformaController.getInstance();

    /**
     * Campo di testo per l'inserimento dell'email.
     */
    private final JTextField emailField;
    
    /**
     * Campo di testo protetto per l'inserimento della password.
     */
    private final JPasswordField passwordField;
    
    /**
     * Pannello principale che contiene i componenti dell'interfaccia.
     */
    private final JPanel contentPanel;

    /**
     * Costruttore che crea e configura la schermata di login dell'applicazione.
     * Inizializza tutti i componenti dell'interfaccia, gestisce il layout e gli eventi.
     */
    public LoginFrame() {

        setTitle("Poesie Brevi - Login");
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

        JLabel titoloLabel = UIUtils.titolo("Benvenuto in Poesie Brevi", 0, 20);

        JLabel emailLabel = UIUtils.label("Email", 20, 80, 14);
        emailField = UIUtils.textField("Inserisci la tua email", 20, 105);

        JLabel passwordLabel = UIUtils.label("Password", 20, 150, 14);
        passwordField = UIUtils.passwordField("Inserisci la tua password", 20, 175);

        JLabel passwordDimenticataLabel = UIUtils.label("Hai dimenticato la password?", UIUtils.CONTENT_WIDTH - 175, 215, 12);
        passwordDimenticataLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20, 245, UIUtils.CONTENT_WIDTH - 40, 40);
        loginButton.setBorder(BorderFactory.createLineBorder(UIUtils.BORDER_COLOR, 1));
        loginButton.setForeground(UIUtils.TEXT_COLOR);
        loginButton.setFont(new Font(UIUtils.FONT, Font.BOLD, 14));
        loginButton.setBackground(UIUtils.BUTTON_COLOR);

        JLabel signUpLabel = new JLabel("Non hai un account? Registrati");
        signUpLabel.setFont(new Font(UIUtils.FONT, Font.PLAIN, 13));
        signUpLabel.setForeground(UIUtils.BUTTON_COLOR);
        signUpLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        signUpLabel.setBounds(0, 295, UIUtils.CONTENT_WIDTH, 20);

        contentPanel.add(titoloLabel);
        contentPanel.add(emailLabel);
        contentPanel.add(emailField);
        contentPanel.add(passwordLabel);
        contentPanel.add(passwordField);
        contentPanel.add(passwordDimenticataLabel);
        contentPanel.add(loginButton);
        contentPanel.add(signUpLabel);

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

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Per favore, inserisci email e password.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(!UIUtils.validaEmail(email)) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Inserisci un indirizzo email valido", "Errore di login", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    if (piattaformaController.esisteUtente(email)) {
                        if (piattaformaController.autenticaUtente(email, password)) {

                            System.out.println(email + " ha effettuato il login" + piattaformaController.getCurrentUser());
                            JOptionPane.showMessageDialog(LoginFrame.this, "Login effettuato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new HomeFrame().setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(LoginFrame.this, "Password non corretta", "Errore di login", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Email non registrata", "Errore di login", JOptionPane.ERROR_MESSAGE);
                    }
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Errore durante il login: " + ex.getMessage(),
                            "Errore di sistema", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        passwordDimenticataLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(LoginFrame.this, "Se hai dimenticato la tua password, non puoi recuperarla!", "Password Dimenticata", JOptionPane.WARNING_MESSAGE);
            }
        });

        signUpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new RegistrazioneFrame().setVisible(true);
                System.out.println("Registrazione Cliccata");
            }
        });
    }
}