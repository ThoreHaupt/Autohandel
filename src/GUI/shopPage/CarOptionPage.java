package GUI.shopPage;

import javax.swing.*;

import java.awt.*;

import GUI.UIController;
import Model.ModelComponentes.Car;
import lib.uiComponents.rigitFreeSpace;

public class CarOptionPage extends JPanel {
    Car car;
    UIController uiController;

    public CarOptionPage(UIController uiController, Car car) {
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
