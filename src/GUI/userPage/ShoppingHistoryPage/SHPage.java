package GUI.userPage.ShoppingHistoryPage;

import javax.swing.JPanel;

import Controller.Controller;

import java.awt.*;

import GUI.UIController;
import Model.UserComponentes.User;
import lib.uiComponents.MLLabel;
import lib.uiComponents.rigitFreeSpace;

/**
 * Combindes the EntryDisplayArea and the title mostly
 */
public class SHPage extends JPanel {
    UIController uiController;
    Controller controller;
    User user;

    double displayHorizontalPercentage = 1.0;
    double displayVerticalPercentage = 1.0;
    Dimension priceAreaSize = new Dimension();
    int heightDelta;

    /**
     * creates a new Shopping page
     * @param uiController
     * @param heightDelta
     */
    public SHPage(UIController uiController, int heightDelta) {
        this.uiController = uiController;
        this.controller = uiController.getController();
        user = controller.getUser();

        this.heightDelta = heightDelta;

        buildSHPage();
        uiController.getWindow().addWindowSizeChangeListener(e -> rebuildSH());
        controller.addNewUserLoginListener(e -> reloadSH());
    }

    /**
     * When no user is logged in (/guest) then the user cant see any history, so he sees this
     */
    private void buildDeactivePage() {
        add(new MLLabel(uiController, "please log in to see your shopping history!"));
    }

    /**
     * builds a new Shopping Histroy Page with a delataHight of 0 which means that it fills the entire screen
     * @param uiController
     */
    public SHPage(UIController uiController) {
        this(uiController, 0);
    }

    /**
     * rebuilds after update
     */
    public void rebuildSH() {
        removeAll();
        buildSHPage();
        revalidate();
        repaint();
    }

    /**
     * reloads after the user changes for example
     */
    public void reloadSH() {
        user = controller.getUser();
        rebuildSH();
    }

    /**
     * builds the Actual Panel, pulling all the components together from this package basically
     */
    public void buildSHPage() {

        if (user.isGuest()) {
            buildDeactivePage();
            return;
        }

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.weighty = 0.1;
        c.anchor = GridBagConstraints.CENTER;

        JPanel titlePanel = buildTitleArea();

        add(titlePanel, c);

        c.gridy++;
        c.weightx = displayHorizontalPercentage;
        c.weighty = displayVerticalPercentage;
        c.gridwidth = 1;
        JPanel CartOrdrDisplayPanel = new SHDisplayArea(uiController, user, calculateDisplayDimension());
        add(CartOrdrDisplayPanel, c);

        // adSpace
        c.anchor = GridBagConstraints.CENTER;
        JPanel adSpace = new JPanel();
        adSpace.setBackground(new Color(0, 0, 255));
        c.gridy++;
        c.gridx = 0;
        c.weightx = 1.0;
        c.weighty = 0.2;
        c.gridwidth = 2;
        add(adSpace, c);

    }

    /**
     * calculates the size the scrollpane that shows all the orders can be
     * @return that Dimension
     */
    private Dimension calculateDisplayDimension() {
        int height = uiController.getUsableHeight() - heightDelta;
        int width = uiController.getWindow().getWidth();
        int displayWidth = (int) (width * displayHorizontalPercentage * 0.95);
        int displayHeight = (int) (height * displayVerticalPercentage * 0.95);
        return new Dimension(displayWidth, displayHeight);
    }

    /**
     * builds the title
     * @return
     */
    private JPanel buildTitleArea() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.01;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.CENTER;

        c.fill = GridBagConstraints.HORIZONTAL;

        // Some space to the left of the title
        panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(
                10, 10)), c);
        // Title Label
        c.gridx++;
        c.weightx = 0.99;
        c.anchor = GridBagConstraints.LINE_START;
        MLLabel pageTitle = new MLLabel(uiController, "Shopping History");
        pageTitle.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 40));
        panel.add(pageTitle, c);
        return panel;
    }
}
