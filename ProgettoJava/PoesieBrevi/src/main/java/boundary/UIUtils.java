package boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;


public class UIUtils {
    public static final int CONTENT_WIDTH = 700;
    public static final int CONTENT_HEIGHT = 600;
    public static final int CONTENT_MARGIN_W = 800;
    public static final int CONTENT_MARGIN_H = 700;
    public static final String FONT = "Baskerville";

    public static final Color BACKGROUND_COLOR = new Color(246,238,225);
    public static final Color BORDER_COLOR = new Color(209,196,179);
    public static final Color BUTTON_COLOR = new Color(70,70,70);
    public static final Color ACCENT_COLOR = new Color(185,140,90);
    public static final Color TEXT_COLOR = new Color(0,0,0);

    public static final Color BORDER_SELECTED = new Color(186, 167, 139);


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

    public static JTextField CreaPoesiaTextField(int labelWidth, int y, int fieldWidth, int height) {
        JTextField creaPoesieTextField = new JTextField();
        creaPoesieTextField.setFont(new Font(FONT, Font.PLAIN, 14));
        creaPoesieTextField.setBounds(labelWidth, y, fieldWidth, height);
        creaPoesieTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIUtils.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        return creaPoesieTextField;
    }
    public static JPasswordField passwordField(String tip, int x, int y){
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

    public static JLabel label(String testo, int x, int y, int size) {
        JLabel label = new JLabel(testo);
        label.setFont(new Font(FONT, Font.PLAIN, size));
        label.setForeground(TEXT_COLOR);
        label.setBounds(x, y, 150,20);
        return label;
    }

    public static JLabel label(String testo, int x, int y, int size, int width, int height) {
        JLabel label = new JLabel(testo);
        label.setFont(new Font(FONT, Font.PLAIN, size));
        label.setForeground(TEXT_COLOR);
        label.setBounds(x, y, width,height);
        return label;
    }

    public static JLabel titolo(String testo, int x, int y) {
        JLabel titolo = new JLabel(testo);
        titolo.setFont(new Font(FONT, Font.BOLD, 24));
        titolo.setForeground(TEXT_COLOR);
        titolo.setBounds(x, y, CONTENT_WIDTH, 30);
        titolo.setHorizontalAlignment(SwingConstants.CENTER);

        return titolo;
    }

    public static JLabel titolo(String testo, int x, int y, int size) {
        JLabel titolo = new JLabel(testo);
        titolo.setFont(new Font(FONT, Font.BOLD, size));
        titolo.setForeground(TEXT_COLOR);
        titolo.setBounds(x, y, CONTENT_WIDTH, 30);
        titolo.setHorizontalAlignment(SwingConstants.CENTER);

        return titolo;
    }

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

    public static String formatDateCompact(java.util.Date date) {
        if (date == null) return "";

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yy HH:mm");
        return sdf.format(date);
    }

    /**
     * Verifica se l'email inserita è in un formato corretto, nello specifico
     * - "^[a-zA-Z0-9_+&*-]" indica che l'email deve iniziare con uno o più caratteri alfanumerici
     * - "(?:\.[a-zA-Z0-9_+&*-]+)" indica che opzionalmente può contenenere un punto che divide dei caratteri validi
     * - "@" indica che l'email deve contenenre @ per essere valida
     * - "(?:[a-zA-Z0-9-]+\.)" indica che il dominio deve contenere una o più parole separate da punti
     * - "[a-zA-Z]{2,7}" indica che, essendo un dominio di primo livello deve avere tra le 2 e le 7 lettere

     * Per farlo si stabilisce il pattern (specificato sopra) e tramite il metodo .matches() della classe String si verifica se l'email inserita rispetta il pattern
     */
    public static boolean validaEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
    }

        public static void centerContentPanel(int width, int height, JPanel contentPanel) {
        int x = (width - UIUtils.CONTENT_WIDTH) / 2;
        int y = (height - UIUtils.CONTENT_HEIGHT) / 2;
        contentPanel.setBounds(x, y, UIUtils.CONTENT_WIDTH, UIUtils.CONTENT_HEIGHT);
    }

    public static void setupMainPanel(JPanel mainPanel, int width, int height) {
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(UIUtils.BACKGROUND_COLOR);
                g.fillRect(0, 0, width, height);
            }
        };
        mainPanel.setLayout(null);
    }
    
    public static void setupContentPanel(JPanel contentPanel, JPanel mainPanel, int width, int height) {
        contentPanel = new JPanel(null) {
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
    }
}
