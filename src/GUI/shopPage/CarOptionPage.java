package GUI.shopPage;

import javax.swing.*;

import java.awt.*;

import GUI.UIController;
import Model.ModelComponentes.Car;

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
        this.add(new ShopEntryContent(uiController, car, false));
    }
}
