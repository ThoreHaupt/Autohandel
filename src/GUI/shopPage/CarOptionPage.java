package GUI.shopPage;

import javax.swing.*;

import java.awt.*;

import GUI.UIController;
import Model.ModelComponentes.CarOption;

public class CarOptionPage extends JPanel {
    CarOption car;
    UIController uiController;

    public CarOptionPage(UIController uiController, CarOption car) {
        this.uiController = uiController;
        this.car = car;
        buildCarOptionPage();
    }

    private void buildCarOptionPage() {
        this.setLayout(new BorderLayout());
        this.add(new ShopEntryContent(uiController, car, false));
    }
}
