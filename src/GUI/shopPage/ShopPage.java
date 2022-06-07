package GUI.shopPage;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

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
        add(new pageSideHideMenu(200));
    }

}
