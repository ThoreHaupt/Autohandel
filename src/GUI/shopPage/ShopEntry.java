package GUI.shopPage;

import javax.swing.JPanel;

import GUI.UIController;
import Model.ModelComponentes.CarOption;
import lib.uiComponents.rigitFreeSpace;

import java.awt.*;

public class ShopEntry extends JPanel {
    public ShopEntry(UIController uiController, CarOption model) {
        this.setLayout(new BorderLayout());
        this.add(new ShopEntryContent(uiController, model), BorderLayout.CENTER);
        this.add(new rigitFreeSpace(null, new Dimension(1, 1)), BorderLayout.WEST);
        this.add(new rigitFreeSpace(null, new Dimension(1, 1)), BorderLayout.EAST);
        this.add(new rigitFreeSpace(null, new Dimension(2, 2)), BorderLayout.NORTH);
        this.add(new rigitFreeSpace(null, new Dimension(2, 2)), BorderLayout.SOUTH);
    }

}
