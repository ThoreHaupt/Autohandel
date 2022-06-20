package lib.uiComponents;

import javax.swing.JPanel;
import java.awt.*;

public class MLsortSelector extends JPanel {
    IRButton irButton; // this button selects the direction in which to sort
    MLComboBox comboBox; // for slecting the attribute after which to sort

    public MLsortSelector() {

    }

    public JPanel buildDirectionButton(Dimension n) {
        JPanel panel = new JPanel();
        String[] imagesDirectionSelector = {}; // up and down
        irButton = new IRButton(imagesDirectionSelector, n);
        return panel;

    }
}
