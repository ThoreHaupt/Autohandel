package GUI.shopPage;

import javax.swing.*;

import GUI.UIController;
import Model.ModelComponentes.Product;
import lib.uiComponents.rigitFreeSpace;

import java.awt.*;
import java.awt.event.*;

public class ShopGalleryEntry extends JPanel {
    CarOptionPage carPage;
    UIController uiController;
    ShopEntryContent contents;

    public ShopGalleryEntry(UIController uiController, Product car) {
        this.setLayout(new BorderLayout());
        this.uiController = uiController;
        JButton entry = new JButton();
        entry.setBackground(UIManager.getColor("default"));
        entry.setLayout(new BorderLayout());
        contents = new ShopEntryContent(uiController, car, true);
        entry.add(contents, BorderLayout.CENTER);
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

    public void revalidateBufferSize(int newOptimalSize) {
        contents.revalidateBufferSize(newOptimalSize);
        revalidate();
    }

}
