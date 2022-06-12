package GUI.shopPage;

import javax.swing.*;

import GUI.UIController;
import Model.ModelComponentes.Car;
import lib.uiComponents.rigitFreeSpace;

import java.awt.*;
import java.awt.event.*;

public class ShopGalleryEntry extends JPanel {
    CarOptionPage carPage;

    public ShopGalleryEntry(UIController uiController, Car car) {
        this.setLayout(new BorderLayout());
        JButton entry = new JButton();
        entry.add(new ShopEntryContent(uiController, car, true));
        entry.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                uiController.setMainWindowContent(carPage);
            }

        });

        this.add(entry, BorderLayout.CENTER);
        this.add(new rigitFreeSpace(null, new Dimension(1, 1)), BorderLayout.WEST);
        this.add(new rigitFreeSpace(null, new Dimension(1, 1)), BorderLayout.EAST);
        this.add(new rigitFreeSpace(null, new Dimension(2, 2)), BorderLayout.NORTH);
        this.add(new rigitFreeSpace(null, new Dimension(2, 2)), BorderLayout.SOUTH);
        carPage = new CarOptionPage(uiController, car);

    }

}
