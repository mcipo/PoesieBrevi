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
        JScrollPane scrollPane = UIUtils.createStatBox(
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
        return UIUtils.createStyledTable(data, columns);

    }



}
