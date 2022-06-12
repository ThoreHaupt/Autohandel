package GUI.shopPage;

import javax.swing.*;
import javax.swing.SpringLayout.Constraints;

import GUI.UIController;
import Model.Model;
import Model.ModelComponentes.Car;
import Model.UserComponentes.Filter;
import lib.uiComponents.pageSideHideMenu;

import java.awt.*;

public class ShopPage extends JPanel {

    UIController uiController;
    Filter filter;

    /**
     * 
     */
    public ShopPage(UIController uiController) {
        this.uiController = uiController;
        this.filter = uiController.getController().getUser().getFilter();
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

        backPanel.add(new ShopGalleryEntry(uiController, new Car()));
        backPanel.add(new ShopGalleryEntry(uiController, new Car()));
        backPanel.add(new ShopGalleryEntry(uiController, new Car()));

        loadEntriesFromModel(filter, backPanel);

        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new BorderLayout());
        returnPanel.add(thisScrollPanel, BorderLayout.CENTER);

        return returnPanel;
    }

    public JPanel createSideMenu() {
        return new JPanel();
    }

    public void loadEntriesFromModel(Filter filter, JPanel panel) {
        ShopGalleryEntry[] entries = uiController.getController().getOptions(filter);
        panel.removeAll();
        panel.repaint();
        for (int i = 0; i < entries.length; i++) {
            panel.add(entries[i]);
        }
        panel.add(Box.createVerticalGlue());
    }

    public void setFilter(Filter filter) {
        if (this.filter.getUser().getUserName().equals("Guest")) {

        }
    }

}
