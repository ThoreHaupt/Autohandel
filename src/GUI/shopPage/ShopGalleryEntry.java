package GUI.shopPage;

import javax.swing.JButton;
import javax.swing.JPanel;

import GUI.UIController;
import Model.ModelComponentes.CarOption;
import lib.uiComponents.rigitFreeSpace;

import java.awt.*;
import java.awt.event.*;

public class ShopGalleryEntry extends JButton {
    CarOptionPage carPage;

    public ShopGalleryEntry(UIController uiController, CarOption car) {
        this.setLayout(new BorderLayout());
        this.add(new ShopEntryContent(uiController, car, true), BorderLayout.CENTER);
        this.add(new rigitFreeSpace(null, new Dimension(1, 1)), BorderLayout.WEST);
        this.add(new rigitFreeSpace(null, new Dimension(1, 1)), BorderLayout.EAST);
        this.add(new rigitFreeSpace(null, new Dimension(2, 2)), BorderLayout.NORTH);
        this.add(new rigitFreeSpace(null, new Dimension(2, 2)), BorderLayout.SOUTH);
        carPage = new CarOptionPage(uiController, car);
        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                uiController.setMainWindowContent(carPage);
            }

        });
    }

}
