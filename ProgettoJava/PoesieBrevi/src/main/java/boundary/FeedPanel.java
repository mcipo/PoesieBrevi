package boundary;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import controller.PoesiaController;
import entity.Poesia;
import entity.User;

/// Finito
public class FeedPanel extends JPanel {

    public FeedPanel(User currentUser) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        JLabel titoloLabel = UIUtils.titolo("Ultime poesie pubblicate", 0, 0, 16);
        add(titoloLabel, BorderLayout.NORTH);

        JPanel poesieContainer = new JPanel();
        poesieContainer.setLayout(new BoxLayout(poesieContainer, BoxLayout.Y_AXIS));
        poesieContainer.setBackground(Color.WHITE);

        try {
            PoesiaController controller = new PoesiaController();
            List<Poesia> poesie = controller.getUltimePoesiePerFeed(currentUser.getId(), 5);

            if (poesie.isEmpty()) {
                JLabel noPoesie = new JLabel("Nessuna poesia disponibile nel feed");
                noPoesie.setFont(new Font(UIUtils.FONT, Font.ITALIC, 14));
                noPoesie.setAlignmentX(Component.CENTER_ALIGNMENT);
                poesieContainer.add(Box.createVerticalStrut(20));
                poesieContainer.add(noPoesie);
            } else {
                for (Poesia poesia : poesie) {
                    JPanel poesiePanel = new PoesiaDisplayPanel(poesia, currentUser);
                    poesieContainer.add(poesiePanel);
                    poesieContainer.add(Box.createVerticalStrut(15));
                }
            }
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Errore nel caricamento delle poesie");
            errorLabel.setFont(new Font(UIUtils.FONT, Font.ITALIC, 14));
            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            poesieContainer.add(errorLabel);
        }

        JScrollPane scrollPane = new JScrollPane(poesieContainer);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }
}
