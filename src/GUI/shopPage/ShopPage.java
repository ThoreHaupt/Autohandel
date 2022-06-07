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
        pageSideHideMenu menuObject = new pageSideHideMenu(200);
        JPanel mainPanel = menuObject.getMainPage();
        add(menuObject);
        mainPanel.add(new Label("hahahha"));
    }

}
