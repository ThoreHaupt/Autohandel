package GUI.shopPage;

import javax.swing.*;
import javax.swing.SpringLayout.Constraints;

import GUI.UIController;
import Model.Model;
import Model.ModelComponentes.CarOption;
import lib.uiComponents.pageSideHideMenu;

import java.awt.*;

public class ShopPage extends JPanel {

    UIController uiController;

    /**
     * 
     */
    public ShopPage(UIController uiController) {
        this.uiController = uiController;
        this.setLayout(new GridLayout());
        createShopPage();
    }

    public void createShopPage() {
        add(new pageSideHideMenu(createMainPage(), createSideMenu(), 200));
    }

    public JPanel createMainPage() {

        // hier ist was kaputt!
        JPanel backPanel = new JPanel();
        // backPanel.setPreferredSize(new Dimension(1000, 2000));
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.PAGE_AXIS));

        JScrollPane thisScrollPanel = new JScrollPane(backPanel);

        backPanel.add(new ShopEntry(uiController, new CarOption()));
        backPanel.add(new ShopEntry(uiController, new CarOption()));
        backPanel.add(new ShopEntry(uiController, new CarOption()));
        backPanel.add(Box.createVerticalGlue());

        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new BorderLayout());
        returnPanel.add(thisScrollPanel, BorderLayout.CENTER);

        return returnPanel;
    }

    public JPanel createSideMenu() {
        return new JPanel();
    }
}
