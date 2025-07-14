package boundary;

import controller.PiattaformaController;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class StatisticheFrame extends JFrame {

    private PiattaformaController piattaformaController = PiattaformaController.getInstance();

    private JPanel contentPanel;

    private JPanel mainPanel;
    public StatisticheFrame() {
        setTitle("Poesie Brevi - Dashboard");
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

    }

    private void setupHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBounds(20, 20, UIUtils.CONTENT_WIDTH - 40, 50);

        String username = piattaformaController.getCurrentUser().getProfilo().getUsername();

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);

        JLabel welcomeLabel = UIUtils.titolo("Benvenuto " + username, 0, 0, 18);
        leftPanel.add(welcomeLabel);
        headerPanel.add(leftPanel, BorderLayout.WEST);

        JButton profiloButton = UIUtils.bottone("Profilo", Font.PLAIN, 14);

        profiloButton.addActionListener(_ -> {
            dispose();
            new ProfiloFrame(false).setVisible(true);
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

        JPanel statsAutoriPanel = new StatsAutoriPanel();
        JPanel statsPoesiePanel = new StatsPoesiePanel();

        tabbedPane.addTab("Statistiche Autori", null, statsAutoriPanel, "Visualizza l'elenco di autori più attivi");
        tabbedPane.addTab("Statistiche Poesie", null, statsPoesiePanel, "Visualizza le poesie con più interazioni");

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
                if (!isSelected)
                    return;
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
}


