package GUI.userPage;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import GUI.UIController;
import GUI.userPage.cartPage.CartPage;
import Model.UserComponentes.User;
import lib.uiComponents.MLTabbedPane;

public class UserPage extends JPanel {
    UIController uiController;
    User user;
    MLTabbedPane tabbedPane;

    public UserPage(UIController uiController) {
        this.uiController = uiController;
        this.user = uiController.getController().getCurrentUser();
        buildUserPage();
    }

    public void buildUserPage() {
        JPanel panel = new JPanel();

        tabbedPane = new MLTabbedPane(uiController);
        tabbedPane.addTab("shopping Cart", new CartPage(uiController, 100));
        tabbedPane.addTab("Purchase History", buildHistroyPage());
        tabbedPane.addTab("User Settings", buildUserSettingPage());

        panel.add(tabbedPane);

        this.add(panel);
    }

    private JPanel buildUserSettingPage() {
        JPanel panel = new JPanel();
        return panel;
    }

    public JPanel buildHistroyPage() {
        JPanel panel = new JPanel();
        return panel;
    }

    public void setTab(int tab) {
        if (tab > 2)
            tab = 0;
        tabbedPane.setSelectedIndex(tab);
    }

}
