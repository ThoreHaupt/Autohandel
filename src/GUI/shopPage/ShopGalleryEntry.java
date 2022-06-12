package GUI.shopPage;

import javax.swing.JButton;
import javax.swing.JPanel;

import GUI.UIController;
import Model.ModelComponentes.CarOption;
import lib.uiComponents.rigitFreeSpace;

import java.awt.*;

public class ShopGalleryEntry extends JButton {
    public ShopGalleryEntry(UIController uiController, CarOption model) {
        this.setLayout(new BorderLayout());
        this.add(new ShopEntryContent(uiController, model, true), BorderLayout.CENTER);
        this.add(new rigitFreeSpace(null, new Dimension(1, 1)), BorderLayout.WEST);
        this.add(new rigitFreeSpace(null, new Dimension(1, 1)), BorderLayout.EAST);
        this.add(new rigitFreeSpace(null, new Dimension(2, 2)), BorderLayout.NORTH);
        this.add(new rigitFreeSpace(null, new Dimension(2, 2)), BorderLayout.SOUTH);
    }

}
