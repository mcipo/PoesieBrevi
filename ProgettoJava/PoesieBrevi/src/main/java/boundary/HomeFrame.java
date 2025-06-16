package boundary;

import entity.User;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.event.*;

/// Finito bisogna vedere i metodi overridden
public class HomeFrame extends JFrame {

    private final User currentUser;
    private JPanel contentPanel;
    private JPanel mainPanel;

    public HomeFrame(User user) {
        this.currentUser = user;

        setTitle("Poesie Brevi - Home");
        setSize(UIUtils.CONTENT_MARGIN_W, UIUtils.CONTENT_MARGIN_H);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 700));

        mainPanel = UIUtils.setupMainPanel(getWidth(), getHeight());

        contentPanel = UIUtils.setupContentPanel(mainPanel, getWidth(), getHeight());

        setupHeaderPanel();

        setupTabbedPane();

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

        String username = currentUser.getProfilo().getUsername();

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);

        JLabel welcomeLabel = UIUtils.titolo("Benvenuto " + username, 0, 0, 18);
        leftPanel.add(welcomeLabel);
        headerPanel.add(leftPanel, BorderLayout.WEST);

        JButton profiloButton = UIUtils.bottone("Profilo", Font.PLAIN, 14);

        profiloButton.addActionListener(_ -> {
            dispose();
            new ProfiloFrame(currentUser).setVisible(true);
        });
        headerPanel.add(profiloButton, BorderLayout.EAST);

        contentPanel.add(headerPanel);
    }

    private void setupTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(20, 80, UIUtils.CONTENT_WIDTH - 40, UIUtils.CONTENT_HEIGHT - 100);
        tabbedPane.setFont(new Font(UIUtils.FONT, Font.PLAIN, 14));
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setForeground(UIUtils.TEXT_COLOR);

        tabbedPane.setBorder(null);

        JPanel feedPanel = new FeedPanel(currentUser);
        JPanel miePoesiePanel = new MiePoesiePanel(currentUser);
        JPanel mieRaccoltePanel = new MieRaccoltePanel(currentUser);

        tabbedPane.addTab("Feed", null, feedPanel, "Visualizza il feed delle poesie");
        tabbedPane.addTab("Le mie Poesie", null, miePoesiePanel, "Visualizza le tue poesie");
        tabbedPane.addTab("Le mie Raccolte", null, mieRaccoltePanel, "Visualizza le tue raccolte");

        tabbedPane.setSelectedIndex(0);

        tabbedPane.setUI(new BasicTabbedPaneUI() {
            /**
             * Imposta i valori predefiniti per il layout e il rendering del JTabbedPane
             * - tabAreaInsets: indica i margini dell'area totale delle tab
             * - selectedTabPadInsets: margini aggiuntivi per il tab selezionato
             * - tabInsets: margini interni di ogni tab
             * - contentBorderInsets: margini del bordo intorno al contenuto del tab
             */
            @Override
            protected void installDefaults() {
                super.installDefaults();
                tabAreaInsets = new Insets(4, 4, 0, 4);
                selectedTabPadInsets = new Insets(0, 0, 0, 0);
                tabInsets = new Insets(6, 12, 6, 12);
                contentBorderInsets = new Insets(1, 0, 0, 0);
            }

            /**
             * Disegnamo solo il bordo inferiore per il tab selezionato
             */
            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                    int x, int y, int w, int h, boolean isSelected) {
                if (!isSelected)
                    return;
                g.setColor(UIUtils.ACCENT_COLOR);
                g.fillRect(x, y + h - 2, w, 2);
            }

            /**
             * Disegna lo sfondo del tab, per avere lo sfondo bianco, è lasciato trasparente
             */
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                    int x, int y, int w, int h, boolean isSelected) {

            }

            /**
             * Disegna un indicatore per il mouse, viene lasciato vuoto per non avere nulla
             */
            @Override
            protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects,
                    int tabIndex, Rectangle iconRect, Rectangle textRect,
                    boolean isSelected) {

            }

            /**
             * Disegna il bordo superiore del tab, lasciato vuoto perchè non si vuole un bordo superiore
             */
            @Override
            protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex,
                    int x, int y, int w, int h) {

            }

            /**
             * Disegna il bordo del contenuto, lasciato vuoto perchè non lo si vuole colorare
             */

            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {

            }

            /**
             * Calcola la larghezza del tab, aumentandone la larghezza di 10
             */
            @Override
            protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
                return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 10;
            }
            /**
             * Calcola l'altezza del tab, aumentandola 4
             */
            @Override
            protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
                return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) + 4;
            }
        });

        contentPanel.add(tabbedPane);
    }

}