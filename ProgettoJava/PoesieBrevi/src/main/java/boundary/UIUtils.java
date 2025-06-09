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

}
