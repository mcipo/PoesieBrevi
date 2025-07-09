package boundary;


import controller.PiattaformaController;
import controller.ProfiloController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StatsAutoriPanel extends JPanel {
    public StatsAutoriPanel(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        JScrollPane scrollPane = UIUtils.createStatBox(
                createAutoriTable(),
                "Autori più attivi",
                200, 200
        );
        add(scrollPane, BorderLayout.CENTER);
    }
    private JTable createAutoriTable() {
        String[] columns = {"Posizione", "Autore", "Poesie pubblicate"};

        List<Object[]> rows = new ArrayList<>();
        List<int[]> userStats = PiattaformaController.getUserConPiuPoesie();
        for (int i = 0; i < userStats.size(); i++) {
            int[] autoreStat = userStats.get(i);
            Object[] row = new Object[columns.length];
            row[0] = i + 1;
            row[1] = ProfiloController.caricaProfilo(autoreStat[0]).getUsername();
            row[2] = autoreStat[1];
            rows.add(row);
        }

        Object[][] data = rows.toArray(new Object[0][columns.length]);
        return UIUtils.createStyledTable(data, columns);

    }
}
