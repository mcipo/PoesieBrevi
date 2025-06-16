package boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

/**
 * Classe di utilità che fornisce costanti, metodi e funzioni comuni
 * per la creazione e la gestione dell'interfaccia utente dell'applicazione.
 * Include dimensioni standard, colori, funzioni per creare componenti UI coerenti
 * e utility per la validazione e la formattazione.
 */
public class UIUtils {
    /**
     * Larghezza standard del contenuto dell'interfaccia.
     */
    public static final int CONTENT_WIDTH = 700;
    
    /**
     * Altezza standard del contenuto dell'interfaccia.
     */
    public static final int CONTENT_HEIGHT = 600;
    
    /**
     * Larghezza standard della finestra con margini.
     */
    public static final int CONTENT_MARGIN_W = 800;
    
    /**
     * Altezza standard della finestra con margini.
     */
    public static final int CONTENT_MARGIN_H = 700;
    
    /**
     * Font standard utilizzato nell'applicazione.
     */
    public static final String FONT = "Baskerville";

    /**
     * Colore di sfondo principale dell'applicazione.
     */
    public static final Color BACKGROUND_COLOR = new Color(246,238,225);
    
    /**
     * Colore standard per i bordi dei componenti.
     */
    public static final Color BORDER_COLOR = new Color(209,196,179);
    
    /**
     * Colore standard per i pulsanti.
     */
    public static final Color BUTTON_COLOR = new Color(70,70,70);
    
    /**
     * Colore di accento per elementi evidenziati o selezionati.
     */
    public static final Color ACCENT_COLOR = new Color(185,140,90);
    
    /**
     * Colore standard del testo.
     */
    public static final Color TEXT_COLOR = new Color(0,0,0);

    /**
     * Colore dei bordi per gli elementi selezionati.
     */
    public static final Color BORDER_SELECTED = new Color(186, 167, 139);

    /**
     * Crea un campo di testo standard con posizione specificata.
     *
     * @param tip Suggerimento (tooltip) del campo.
     * @param x Coordinata X del campo.
     * @param y Coordinata Y del campo.
     * @return JTextField configurato secondo lo stile dell'applicazione.
     */
    public static JTextField textField(String tip, int x, int y) {
        JTextField textField = new JTextField();
        textField.setFont(new Font(FONT, Font.PLAIN, 14));
        textField.setBackground(new Color(250, 250, 255)); ///Da cambiare con un colore migliore della palete
        textField.setToolTipText(tip);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        textField.setBounds(x, y, CONTENT_WIDTH - 40, 30);
        return textField;
    }

    /**
     * Crea un campo di testo specifico per la creazione di poesie.
     *
     * @param labelWidth Larghezza dell'etichetta associata.
     * @param y Coordinata Y del campo.
     * @param fieldWidth Larghezza del campo.
     * @param height Altezza del campo.
     * @return JTextField configurato per l'inserimento di dati nelle poesie.
     */
    public static JTextField CreaPoesiaTextField(int labelWidth, int y, int fieldWidth, int height) {
        JTextField creaPoesieTextField = new JTextField();
        creaPoesieTextField.setFont(new Font(FONT, Font.PLAIN, 14));
        creaPoesieTextField.setBounds(labelWidth, y, fieldWidth, height);
        creaPoesieTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIUtils.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        return creaPoesieTextField;
    }
    
    /**
     * Crea un campo password standard con posizione specificata.
     *
     * @param tip Suggerimento (tooltip) del campo.
     * @param x Coordinata X del campo.
     * @param y Coordinata Y del campo.
     * @return JPasswordField configurato secondo lo stile dell'applicazione.
     */
    public static JPasswordField passwordField(String tip, int x, int y) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font(FONT, Font.PLAIN, 12));
        passwordField.setBackground(new Color(250, 250, 255));
        passwordField.setToolTipText(tip);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        passwordField.setBounds(x, y, CONTENT_WIDTH - 40, 30);
        return passwordField;
    }

    /**
     * Crea un'etichetta standard con posizione e dimensione del testo specificate.
     *
     * @param testo Testo dell'etichetta.
     * @param x Coordinata X dell'etichetta.
     * @param y Coordinata Y dell'etichetta.
     * @param size Dimensione del testo.
     * @return JLabel configurato secondo lo stile dell'applicazione.
     */
    public static JLabel label(String testo, int x, int y, int size) {
        JLabel label = new JLabel(testo);
        label.setFont(new Font(FONT, Font.PLAIN, size));
        label.setForeground(TEXT_COLOR);
        label.setBounds(x, y, 150,20);
        return label;
    }

    /**
     * Crea un'etichetta standard con posizione, dimensione del testo e dimensioni specificate.
     *
     * @param testo Testo dell'etichetta.
     * @param x Coordinata X dell'etichetta.
     * @param y Coordinata Y dell'etichetta.
     * @param size Dimensione del testo.
     * @param width Larghezza dell'etichetta.
     * @param height Altezza dell'etichetta.
     * @return JLabel configurato secondo lo stile dell'applicazione.
     */
    public static JLabel label(String testo, int x, int y, int size, int width, int height) {
        JLabel label = new JLabel(testo);
        label.setFont(new Font(FONT, Font.PLAIN, size));
        label.setForeground(TEXT_COLOR);
        label.setBounds(x, y, width,height);
        return label;
    }

    /**
     * Crea un'etichetta di titolo standard con posizione specificata.
     *
     * @param testo Testo del titolo.
     * @param x Coordinata X del titolo.
     * @param y Coordinata Y del titolo.
     * @return JLabel configurato come titolo secondo lo stile dell'applicazione.
     */
    public static JLabel titolo(String testo, int x, int y) {
        JLabel titolo = new JLabel(testo);
        titolo.setFont(new Font(FONT, Font.BOLD, 24));
        titolo.setForeground(TEXT_COLOR);
        titolo.setBounds(x, y, CONTENT_WIDTH, 30);
        titolo.setHorizontalAlignment(SwingConstants.CENTER);

        return titolo;
    }

    /**
     * Crea un'etichetta di titolo standard con posizione e dimensione specificate.
     *
     * @param testo Testo del titolo.
     * @param x Coordinata X del titolo.
     * @param y Coordinata Y del titolo.
     * @param size Dimensione del testo.
     * @return JLabel configurato come titolo secondo lo stile dell'applicazione.
     */
    public static JLabel titolo(String testo, int x, int y, int size) {
        JLabel titolo = new JLabel(testo);
        titolo.setFont(new Font(FONT, Font.BOLD, size));
        titolo.setForeground(TEXT_COLOR);
        titolo.setBounds(x, y, CONTENT_WIDTH, 30);
        titolo.setHorizontalAlignment(SwingConstants.CENTER);

        return titolo;
    }

    /**
     * Crea un pulsante standard con stile specificato.
     *
     * @param testo Testo del pulsante.
     * @param fontStyle Stile del font (Font.PLAIN, Font.BOLD, Font.ITALIC).
     * @param dimensione Dimensione del testo.
     * @return JButton configurato secondo lo stile dell'applicazione.
     */
    public static JButton bottone(String testo, int fontStyle, int dimensione) {
        JButton button = new JButton(testo);
        button.setBounds(CONTENT_MARGIN_W, CONTENT_MARGIN_H, CONTENT_WIDTH, CONTENT_HEIGHT);
        button.setFont(new Font(FONT, fontStyle, dimensione));
        button.setBackground(ACCENT_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setMargin(new Insets(5, 5, 5, 5));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(ACCENT_COLOR.darker());
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(ACCENT_COLOR);
            }
        });
        return button;
    }

    /**
     * Formatta una data nel formato compatto.
     *
     * @param date Data da formattare.
     * @return Stringa con la data formattata.
     */
    public static String formatDateCompact(java.util.Date date) {
        if (date == null) return "";

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yy HH:mm");
        return sdf.format(date);
    }

    /**
     * Verifica se l'email inserita è in un formato valido.
     * Controlla che l'email contenga caratteri alfanumerici, un simbolo @,
     * un dominio valido e un dominio di primo livello corretto.
     *
     * @param email Email da validare.
     * @return true se l'email è in formato valido, false altrimenti.
     */
    public static boolean validaEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
    }

    /**
     * Centra un pannello di contenuto all'interno della finestra.
     *
     * @param width Larghezza della finestra.
     * @param height Altezza della finestra.
     * @param contentPanel Pannello da centrare.
     */
    public static void centerContentPanel(int width, int height, JPanel contentPanel) {
        int x = (width - UIUtils.CONTENT_WIDTH) / 2;
        int y = (height - UIUtils.CONTENT_HEIGHT) / 2;
        contentPanel.setBounds(x, y, UIUtils.CONTENT_WIDTH, UIUtils.CONTENT_HEIGHT);
    }

    /**
     * Configura un pannello principale per una finestra.
     * Imposta layout e dimensioni appropriate.
     *
     * @param width Larghezza della finestra.
     * @param height Altezza della finestra.
     * @return JPanel configurato come pannello principale.
     */
    public static JPanel setupMainPanel(int width, int height) {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(UIUtils.BACKGROUND_COLOR);
                g.fillRect(0, 0, width, height);
            }
        };
        mainPanel.setLayout(null);
        return mainPanel;
    }
    
    /**
     * Configura un pannello di contenuto all'interno di un pannello principale.
     * Imposta layout e bordi appropriati.
     *
     * @param mainPanel Pannello principale contenitore.
     * @param width Larghezza della finestra.
     * @param height Altezza della finestra.
     * @return JPanel configurato come pannello di contenuto.
     */
    public static JPanel setupContentPanel(JPanel mainPanel, int width, int height) {
        JPanel contentPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, width, height, 15, 15);
                g2d.setColor(UIUtils.BORDER_COLOR);
                g2d.drawRoundRect(0, 0, width - 1, height - 1, 15, 15);
            }
        };
        contentPanel.setOpaque(false);
        contentPanel.setBounds(0, 0, UIUtils.CONTENT_WIDTH, UIUtils.CONTENT_HEIGHT);
        mainPanel.add(contentPanel);
        return contentPanel;
    }
}
