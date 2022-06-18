package GUI.cartPage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

import Controller.Controller;

import java.awt.*;

import GUI.UIController;
import Model.UserComponentes.Cart;
import lib.uiComponents.MLButton;
import lib.uiComponents.MLLabel;
import lib.uiComponents.rigitFreeSpace;

public class CartPage extends JPanel {
    UIController uiController;
    Controller controller;
    Cart cart;

    double displayHorizontalPercentage = 0.8;
    double displayVerticalPercentage = 0.8;

    Dimension priceAreaSize = new Dimension();

    public CartPage(UIController uiController) {
        this.uiController = uiController;
        this.controller = uiController.getController();
        cart = controller.getUser().getCart();

        buildCartPage();
        uiController.getWindow().addWindowSizeChangeListener(e -> rebuildCart());
        controller.addNewUserLoginListener(e -> reloadCart());
    }

    public void rebuildCart() {
        removeAll();
        buildCartPage();
        revalidate();
        repaint();
    }

    public void reloadCart() {
        cart = controller.getUser().getCart();
        rebuildCart();
    }

    public void buildCartPage() {
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
        JPanel CartOrdrDisplayPanel = new CartDisplayArea(uiController, cart, calculateDisplayDimension());
        add(CartOrdrDisplayPanel, c);

        c.gridx++;
        c.weightx = 0.3;
        c.anchor = GridBagConstraints.PAGE_START;
        JPanel pricePayArea = buildPricePaySaveArea();
        add(pricePayArea, c);

        // Add page with utilities to add to Cart
        // taks 0.3 in vertical space
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
        int height = uiController.getUsableHeight();
        int width = uiController.getWindow().getWidth();
        int displayWidth = (int) (width * displayHorizontalPercentage * 0.95);
        int displayHeight = (int) (height * displayVerticalPercentage * 0.95);
        return new Dimension(displayWidth, displayHeight);
    }

    /**
     * calculates the size the scrollpane that shows all the orders can be
     * @return that Dimension
     */
    private Dimension calculateButtonPriceDimension() {
        int height = uiController.getUsableHeight();
        int width = uiController.getWindow().getWidth();
        int buttonPriceWidth = (int) (width * (1 - displayHorizontalPercentage));
        int buttonPriceHeight = (int) (height * displayVerticalPercentage);
        return new Dimension(buttonPriceWidth, buttonPriceHeight);
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

    private JPanel buildPricePaySaveArea() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.ipadx = 10;
        c.ipady = 5;

        MLLabel priceLabelTag = new MLLabel(uiController, "Total:");
        priceLabelTag.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 20));
        panel.add(priceLabelTag, c);
        c.gridx++;

        JLabel priceLabel = new JLabel();
        priceLabel.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 20));
        priceLabel.setPreferredSize(new Dimension(100, 100));
        priceLabel.setText("0");
        controller.addChangeToCartListener(e -> priceLabel.setText(((Cart) e.getSource()).getTotalPrice() + ""));
        panel.add(priceLabel, c);

        c.gridy++;

        // Some Extra space
        panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(
                200, 5)), c);

        //Order Button
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        MLButton order = new MLButton(uiController, "Order Now!");
        order.setPreferredSize(new Dimension(400, 100));
        order.setBackground(uiController.getDefaultAccentColor());
        order.addActionListener(e -> controller.buyCart());
        panel.add(order, c);

        c.gridy++;
        // Some Extra space
        panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(
                200, 1)), c);

        c.gridy++;
        MLButton exportCart = new MLButton(uiController, "Export ");
        exportCart.setPreferredSize(new Dimension(400, 80));
        controller.exportCurrentCart();
        panel.add(exportCart, c);

        panel.setPreferredSize(calculateButtonPriceDimension());

        return panel;
    }
}
