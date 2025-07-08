package boundary;

import DTO.PoesiaDTO;
import controller.PiattaformaController;
import controller.PoesiaController;
import controller.ProfiloController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;

public class StatsPoesiePanel extends JPanel {
    List<PoesiaDTO> ultimePoesie = PiattaformaController.getPoesieUltimaSettimana();
    public StatsPoesiePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        JLabel titoloLabel = UIUtils.titolo("Poesie pubblicate nell'ultima settimana: "+ ultimePoesie.size(), 0, 0, 16);
        add(titoloLabel, BorderLayout.NORTH);
        JScrollPane scrollPane = createStatBox(
                createPoesieTable(),
                "Poesie con più interazioni",
                200, 200
        );
        add(scrollPane, BorderLayout.CENTER);




    }
    private JTable createPoesieTable() {
        String[] columns = {"Titolo", "Autore", "\uDBC0\uDEB5", "\uDBC0\uDF24", "Totale"};

        List<Object[]> rows = new ArrayList<>();

        for (PoesiaDTO poesiaDTO : ultimePoesie) {
            int cuoriTotali = PoesiaController.getNumCuori(poesiaDTO.getId());
            int commentiTotali = PoesiaController.getNumCommenti(poesiaDTO.getId());
            Object[] row = new Object[columns.length];
            row[0] = poesiaDTO.getTitolo();
            row[1] = ProfiloController.caricaProfilo(poesiaDTO.getAutoreID()).getUsername();
            row[2] = cuoriTotali;
            row[3] = commentiTotali;
            row[4] = cuoriTotali + commentiTotali;
            rows.add(row);
        }

        rows.sort((row1, row2) -> Integer.compare((int) row2[4], (int) row1[4]));
        
        Object[][] data = rows.toArray(new Object[0][columns.length]);
        return createStyledTable(data, columns);

    }
    private JTable createStyledTable(Object[][] data, String[] columns) {
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font(UIUtils.FONT, Font.PLAIN, 13));
        table.setRowHeight(25);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setFocusable(false);

        // Header styling
        table.getTableHeader().setFont(new Font(UIUtils.FONT, Font.BOLD, 13));
        table.getTableHeader().setBackground(UIUtils.ACCENT_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        // Alternating rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (row % 2 == 0) {
                    c.setBackground(Color.WHITE);
                } else {
                    c.setBackground(new Color(248, 248, 248));
                }

                setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
                return c;
            }
        });

        return table;
    }

    private JScrollPane createStatBox(JComponent component, String title, int width, int height) {
        JScrollPane scrollPane = new JScrollPane(component) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 15, 15);

                g2d.dispose();
            }
        };

        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(50, 10, 10, 10),
                title,
                0, 0,
                new Font(UIUtils.FONT, Font.BOLD, 14),
                UIUtils.TEXT_COLOR
        ));
        scrollPane.setPreferredSize(new Dimension(width, height));
        scrollPane.setMinimumSize(new Dimension(width, height));
        scrollPane.setMaximumSize(new Dimension(width, height));
        scrollPane.getViewport().setOpaque(false);

        return scrollPane;
    }



}
