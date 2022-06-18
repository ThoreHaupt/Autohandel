package GUI.shopPage;

import javax.swing.*;

import java.awt.*;

import GUI.UIController;
import Model.ModelComponentes.Product;
import lib.uiComponents.rigitFreeSpace;

public class ProductPage extends JPanel {
    Product car;
    UIController uiController;

    public ProductPage(UIController uiController, Product car) {
        this.uiController = uiController;
        this.car = car;
        buildCarOptionPage();
    }

    private void buildCarOptionPage() {
        this.setLayout(new BorderLayout());
        this.add(new ShopEntryContent(uiController, car, false), BorderLayout.CENTER);
        this.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(20, 1)), BorderLayout.WEST);
        this.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(20, 1)), BorderLayout.EAST);
    }
}
