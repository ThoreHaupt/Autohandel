package GUI.userPage;

import javax.swing.JPanel;

import GUI.UIController;
import GUI.userPage.ShoppingHistoryPage.SHPage;
import GUI.userPage.cartPage.CartPage;
import Model.UserComponentes.User;
import lib.uiComponents.MLTabbedPane;

/**
 * The page that holds all the User Elements. It is tabbed and has 3 options: The Cart, the shopping history and the User settins(which are only
 * the logout button unfortunately because I didnt have enought time)
 */
public class UserPage extends JPanel {
    UIController uiController;
    User user;
    MLTabbedPane tabbedPane;

    /**
     * constructs the userPage
     * @param uiController
     */
    public UserPage(UIController uiController) {
        this.uiController = uiController;
        this.user = uiController.getController().getCurrentUser();
        buildUserPage();
    }

    /**
     * builds the user Page. Mostly the tabs 
     */
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

    public void setTab(int tab) {
        if (tab > 2)
            tab = 0;
        tabbedPane.setSelectedIndex(tab);
    }

}
