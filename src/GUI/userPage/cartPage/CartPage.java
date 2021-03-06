package GUI.userPage.cartPage;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.filechooser.FileSystemView;
import Controller.Controller;

import java.awt.*;
import java.io.File;

import GUI.UIController;
import Model.UserComponentes.Cart;
import lib.uiComponents.MLButton;
import lib.uiComponents.MLLabel;
import lib.uiComponents.rigitFreeSpace;

/**
 * The cart page
 */
public class CartPage extends JPanel {
    UIController uiController;
    Controller controller;
    Cart cart;

    double displayHorizontalPercentage = 0.8;
    double displayVerticalPercentage = 0.8;

    Dimension priceAreaSize = new Dimension();
    int heightDelta;

    JPanel titlePanel;
    JPanel cartOrderDisplayPanel;
    JPanel pricePayArea;
    JPanel adSpace;

    /**
     * sets all intance Variables and returns a new CartPage
     * @param uiController
     * @param heightDelta the amount to make the whole thing smaller so that it fits on the screen nicely
     */
    public CartPage(UIController uiController, int heightDelta) {
        this.uiController = uiController;
        this.controller = uiController.getController();
        cart = controller.getUser().getCart();

        this.heightDelta = heightDelta;

        buildCartPage();
        uiController.getWindow().addWindowSizeChangeListener(e -> rebuildCart());
        controller.addNewUserLoginListener(e -> reloadCart());
    }

    /**
     * a Cart Page with a hight Delta of 0.
     * @param uiController
     */
    public CartPage(UIController uiController) {
        this(uiController, 0);
    }

    /**
     * rebuilds the cart, when there was a change in order
     */
    public void rebuildCart() {
        removeAll();
        buildCartPage();
        revalidate();
        repaint();
    }

    /**
     * reloads the cart / sets the new cart and then rebuilds the cart
     */
    public void reloadCart() {
        cart = controller.getUser().getCart();
        rebuildCart();
    }

    /**
     * builds the cart Page / puts all the regions together you know the drill
     */
    public void buildCartPage() {
        cart = controller.getUser().getCart();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.weighty = 0.1;
        c.anchor = GridBagConstraints.CENTER;
        if (titlePanel == null)
            titlePanel = buildTitleArea();
        add(titlePanel, c);

        c.gridy++;
        c.weightx = displayHorizontalPercentage;
        c.weighty = displayVerticalPercentage;
        c.gridwidth = 1;
        cartOrderDisplayPanel = new CartDisplayArea(uiController, cart, calculateDisplayDimension());
        add(cartOrderDisplayPanel, c);

        c.gridx++;
        c.weightx = 0.3;
        c.anchor = GridBagConstraints.PAGE_START;
        if (pricePayArea == null)
            pricePayArea = buildPricePaySaveArea();
        add(pricePayArea, c);

        // Add page with utilities to add to Cart
        // taks 0.3 in vertical space
        c.anchor = GridBagConstraints.CENTER;
        adSpace = new JPanel();
        //adSpace.setBackground(new Color(0, 0, 255));
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
     * calculates the size the scrollpane that shows all the orders can be
     * @return that Dimension
     */
    private Dimension calculateButtonPriceDimension() {
        int height = uiController.getUsableHeight() - heightDelta;
        int width = uiController.getWindow().getWidth();
        int buttonPriceWidth = (int) (width * (1 - displayHorizontalPercentage));
        int buttonPriceHeight = (int) (height * displayVerticalPercentage);
        return new Dimension(buttonPriceWidth, buttonPriceHeight);
    }

    /**
     * builds the area that holds the cart title
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
        MLLabel pageTitle = new MLLabel(uiController, "Cart");
        pageTitle.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 40));
        panel.add(pageTitle, c);
        return panel;
    }

    /**
     * builds the area that has the buttons used to buy, export or clear the cart
     * @return
     */
    private JPanel buildPricePaySaveArea() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.3;
        c.gridwidth = 1;
        c.ipadx = 10;
        c.ipady = 5;

        MLLabel priceLabelTag = new MLLabel(uiController, "Total:");
        priceLabelTag.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 20));
        panel.add(priceLabelTag, c);
        c.gridx++;

        c.weightx = 0.7;
        JLabel priceLabel = new JLabel();
        priceLabel.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 20));
        priceLabel.setPreferredSize(new Dimension(100, 100));
        priceLabel.setText(cart.getTotalPrice() + "");
        controller.addChangeToCartListener(e -> priceLabel.setText(((Cart) e.getSource()).getTotalPrice() + ""));
        panel.add(priceLabel, c);

        c.gridy++;

        c.weightx = 1.0;
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
        order.setEnabled(false);
        order.setPreferredSize(new Dimension(400, 100));
        order.setBackground(uiController.getDefaultAccentColor());
        order.addActionListener(e -> {
            if (cart.getOrders().length > 0)
                controller.buyCart();
        });

        panel.add(order, c);

        c.gridy++;
        // Some Extra space
        panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(
                200, 1)), c);

        c.gridy++;
        MLButton exportCart = new MLButton(uiController, "Export ");
        exportCart.setPreferredSize(new Dimension(400, 80));
        exportCart.setEnabled(false);
        exportCart.addActionListener(e -> {
            File file = getExportFile();
            if (file != null) {
                controller.exportCurrentCart(file);
            }
        });

        panel.add(exportCart, c);

        c.gridy++;
        // Some Extra space
        panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(
                200, 1)), c);

        c.gridy++;
        MLButton emptyCart = new MLButton(uiController, "clear cart ");
        emptyCart.setPreferredSize(new Dimension(400, 80));
        emptyCart.setEnabled(false);
        emptyCart.setBackground(uiController.getDefaultErrorColor());
        emptyCart.addActionListener(e -> {
            cart.empty();
        });

        panel.add(emptyCart, c);

        uiController.getController().addChangeToCartListener(e -> {
            /* Cart cart = (Cart) e.getSource(); */
            System.out.println("Orders: " + cart.getOrders().length);
            if (cart.getOrders().length > 0) {
                order.setEnabled(true);
                exportCart.setEnabled(true);
                emptyCart.setEnabled(true);
            } else {
                order.setEnabled(false);
                exportCart.setEnabled(false);
                emptyCart.setEnabled(false);
            }
        });

        panel.setPreferredSize(calculateButtonPriceDimension());

        return panel;
    }

    /**
     * Opens a JFileChooser and then returns the selected File.
     * @return
     */
    public File getExportFile() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile();
        } else
            return null;
    }
}
