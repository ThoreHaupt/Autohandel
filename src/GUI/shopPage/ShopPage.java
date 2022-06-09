package GUI.shopPage;

import javax.swing.*;

import lib.uiComponents.pageSideHideMenu;

import java.awt.*;

public class ShopPage extends JPanel {

    /**
     * 
     */
    public ShopPage() {
        this.setLayout(new GridLayout());
        createShopPage();
    }

    public void createShopPage() {
        add(new pageSideHideMenu(createMainPage(), createSideMenu(), 200));
    }

    public JPanel createMainPage() {
        JPanel panel = new JPanel();
        panel.add(new JTextField("hahahha"));
        return panel;
    }

    public JPanel createSideMenu() {
        return new JPanel();
    }
}
