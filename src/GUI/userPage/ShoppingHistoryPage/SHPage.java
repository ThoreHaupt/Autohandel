package GUI.userPage.ShoppingHistoryPage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

import Controller.Controller;

import java.awt.*;

import GUI.UIController;
import Model.UserComponentes.Cart;
import Model.UserComponentes.User;
import lib.uiComponents.MLButton;
import lib.uiComponents.MLLabel;
import lib.uiComponents.rigitFreeSpace;

public class SHPage extends JPanel {
    UIController uiController;
    Controller controller;
    User user;

    double displayHorizontalPercentage = 0.8;
    double displayVerticalPercentage = 0.8;

    Dimension priceAreaSize = new Dimension();
    int heightDelta;

    public SHPage(UIController uiController, int heightDelta) {
        this.uiController = uiController;
        this.controller = uiController.getController();
        user = controller.getUser();

        this.heightDelta = heightDelta;

        buildSHPage();
        uiController.getWindow().addWindowSizeChangeListener(e -> rebuildSH());
        controller.addNewUserLoginListener(e -> reloadSH());
    }

    public SHPage(UIController uiController) {
        this(uiController, 0);
    }

    public void rebuildSH() {
        removeAll();
        buildSHPage();
        revalidate();
        repaint();
    }

    public void reloadSH() {
        user = controller.getUser();
        rebuildSH();
    }

    public void buildSHPage() {
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
        MLLabel pageTitle = new MLLabel(uiController, "Cart");
        pageTitle.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 40));
        panel.add(pageTitle, c);
        return panel;
    }
}
