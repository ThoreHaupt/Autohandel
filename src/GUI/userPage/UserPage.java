package GUI.userPage;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import GUI.UIController;
import GUI.userPage.ShoppingHistoryPage.SHPage;
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
        tabbedPane.addTab("Purchase History", new SHPage(uiController, 100));
        tabbedPane.addTab("User Settings", new UserProfilePage(uiController));

        panel.add(tabbedPane);
        updateonLogin();
        uiController.getController().addNewUserLoginListener(e -> updateonLogin());

        this.add(panel);
    }

    /**
     * disabele tabs if the loge in user is guest
     */
    void updateonLogin() {
        user = uiController.getController().getCurrentUser();
        if (user.isGuest()) {
            for (int i = 1; i < tabbedPane.getTabCount(); i++) {
                tabbedPane.setEnabledAt(i, false);
            }
        } else {
            for (int i = 1; i < tabbedPane.getTabCount(); i++) {
                tabbedPane.setEnabledAt(i, true);
            }
        }

    }

    private JPanel buildUserSettingPage() {
        JPanel panel = new JPanel();
        return panel;
    }

    public void setTab(int tab) {
        if (tab > 2)
            tab = 0;
        tabbedPane.setSelectedIndex(tab);
    }

}
