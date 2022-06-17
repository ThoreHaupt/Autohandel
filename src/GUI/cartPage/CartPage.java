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

public class CartPage extends JPanel {
    UIController uiController;
    Controller controller;
    Cart cart;

    double displayPercentage = 0.7;

    Dimension priceAreaSize = new Dimension();

    public CartPage(UIController uiController) {
        this.uiController = uiController;
        this.controller = uiController.getController();
        cart = controller.getUser().getCart();

        buildCartPage();
    }

    public void reloadCart() {

    }

    public void buildCartPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.weighty = 0.1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;

        JPanel titlePanel = buildTitleArea();

        add(titlePanel, c);

        c.gridy++;
        c.weightx = 0.7;
        c.weighty = 0.7;
        c.gridwidth = 1;
        JPanel CartOrdrDisplayPanel = new CartDisplayArea(uiController, cart.getOrders(), calculateDisplayDimension());
        add(CartOrdrDisplayPanel, c);

        c.gridx++;
        c.weightx = 0.3;
        JPanel pricePayArea = buildPricePaySaveArea();
        add(pricePayArea);

        // Add page with utilities to add to Cart
        // taks 0.3 in vertical space

        JPanel adSpace = new JPanel();
        c.gridy++;
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
        int displayWidth = (int) (width * displayPercentage);
        return new Dimension(height, displayWidth);
    }

    private JPanel buildTitleArea() {
        JPanel panel = new JPanel();
        MLLabel pageTitle = new MLLabel(uiController, "Cart");
        pageTitle.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 20));
        panel.setLayout(new BorderLayout());
        panel.add(pageTitle);
        return panel;
    }

    private JPanel buildPricePaySaveArea() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setSize(priceAreaSize);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;

        MLLabel priceLabelTag = new MLLabel(uiController, "Total:");
        panel.add(priceLabelTag, c);
        c.gridx++;
        JLabel priceLabel = new JLabel();

        panel.add(priceLabel, c);
        c.gridx = 0;
        c.gridy++;
        MLButton order = new MLButton(uiController, "Order Now!");
        panel.add(order, c);

        c.gridy++;

        MLButton exportCart = new MLButton(uiController, "Export ");
        panel.add(exportCart, c);

        return panel;
    }
}
