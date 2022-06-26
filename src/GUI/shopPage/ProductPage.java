package GUI.shopPage;

import javax.swing.*;

import java.awt.*;

import GUI.UIController;
import Model.ModelComponentes.Product;
import lib.uiComponents.rigitFreeSpace;

public class ProductPage extends JPanel {
    Product product;
    UIController uiController;

    /**
     * This creates the Product Page, mosty with the new ShopEntryContent, but this puts some Space around so it looks nice and clean
     * @param uiController the uiController
     * @param product the product
     */
    public ProductPage(UIController uiController, Product product) {
        this.uiController = uiController;
        this.product = product;
        buildCarOptionPage();
    }

    /**
     * builds the ShopEntryConent and the free space around
     */
    private void buildCarOptionPage() {
        this.setLayout(new BorderLayout());
        this.add(new ShopEntryContent(uiController, product, false), BorderLayout.CENTER);
        this.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(20, 1)), BorderLayout.WEST);
        this.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(20, 1)), BorderLayout.EAST);
    }
}
