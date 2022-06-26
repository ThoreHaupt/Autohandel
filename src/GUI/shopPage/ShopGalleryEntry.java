package GUI.shopPage;

import javax.swing.*;

import GUI.UIController;
import Model.ModelComponentes.Product;
import lib.uiComponents.rigitFreeSpace;

import java.awt.*;
import java.awt.event.*;

public class ShopGalleryEntry extends JPanel {
    ProductPage productPage;
    UIController uiController;
    ShopEntryContent contents;

    public ShopGalleryEntry(UIController uiController, Product product) {
        this.setLayout(new BorderLayout());
        this.uiController = uiController;
        JButton entry = new JButton();
        entry.setBackground(UIManager.getColor("default"));
        entry.setLayout(new BorderLayout());
        contents = new ShopEntryContent(uiController, product, true);
        entry.add(contents, BorderLayout.CENTER);
        entry.addActionListener(e -> uiController.setMainWindowContent(productPage));

        this.add(entry, BorderLayout.CENTER);
        this.add(new rigitFreeSpace(null, new Dimension(1, 1)), BorderLayout.WEST);
        this.add(new rigitFreeSpace(null, new Dimension(1, 1)), BorderLayout.EAST);
        this.add(new rigitFreeSpace(null, new Dimension(2, 2)), BorderLayout.NORTH);
        this.add(new rigitFreeSpace(null, new Dimension(2, 2)), BorderLayout.SOUTH);

        if (product.getProductPage() == null) {
            productPage = new ProductPage(uiController, product);
            product.setPrdoductPage(productPage);
        } else {
            productPage = product.getProductPage();
        }

    }

    public void revalidateBufferSize(int newOptimalSize) {
        contents.revalidateBufferSize(newOptimalSize);
        revalidate();
    }

}
