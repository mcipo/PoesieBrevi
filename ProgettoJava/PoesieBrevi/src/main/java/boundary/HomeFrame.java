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

        setupMainPanel();

        setupContentPanel();

        setupHeaderPanel();

        setupTabbedPane();

        add(mainPanel);
        centerContentPanel();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                centerContentPanel();
            }
        });
    }

    private void setupMainPanel() {
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(UIUtils.BACKGROUND_COLOR);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);
    }

    private void setupContentPanel() {
        contentPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(UIUtils.BORDER_COLOR);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };
        contentPanel.setOpaque(false);
        contentPanel.setBounds(0, 0, UIUtils.CONTENT_WIDTH, UIUtils.CONTENT_HEIGHT);
        mainPanel.add(contentPanel);
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
            @Override
            protected void installDefaults() {
                super.installDefaults();
                tabAreaInsets = new Insets(4, 4, 0, 4);
                selectedTabPadInsets = new Insets(0, 0, 0, 0);
                tabInsets = new Insets(6, 12, 6, 12);
                contentBorderInsets = new Insets(1, 0, 0, 0);
            }

            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                                          int x, int y, int w, int h, boolean isSelected) {
                if (!isSelected) return;
                g.setColor(UIUtils.ACCENT_COLOR);
                g.fillRect(x, y + h - 2, w, 2);
            }

            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                                              int x, int y, int w, int h, boolean isSelected) {

            }

            @Override
            protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects,
                                               int tabIndex, Rectangle iconRect, Rectangle textRect,
                                               boolean isSelected) {

            }

            @Override
            protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex,
                                                     int x, int y, int w, int h) {

            }

            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {

            }

            @Override
            protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
                return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 10;
            }

            @Override
            protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
                return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) + 4;
            }
        });

        contentPanel.add(tabbedPane);
    }

    private void centerContentPanel() {
        int x = (getWidth() - UIUtils.CONTENT_WIDTH) / 2;
        int y = (getHeight() - UIUtils.CONTENT_HEIGHT) / 2;
        contentPanel.setBounds(x, y, UIUtils.CONTENT_WIDTH, UIUtils.CONTENT_HEIGHT);
        repaint();
    }
}