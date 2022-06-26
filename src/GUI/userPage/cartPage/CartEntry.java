package GUI.userPage.cartPage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.plaf.basic.BasicSpinnerUI;

import Controller.Controller;
import GUI.UIController;
import GUI.shopPage.ProductPage;
import Model.ModelComponentes.Product;
import Model.UserComponentes.Order;
import lib.uiComponents.ImageButton;
import lib.uiComponents.rigitFreeSpace;

import java.awt.*;

/**
 * Each Entry in the Cart display
 */
public class CartEntry extends JButton {

    UIController uiController;
    Controller controller;
    Order order;
    Product product;
    Dimension size;
    static int height = 60;
    ProductPage productPage;

    /**
     * @param uiController
     * @param order
     */
    public CartEntry(UIController uiController, Order order, int width) {
        this.uiController = uiController;
        this.controller = uiController.getController();
        this.order = order;
        this.product = order.getProduct();
        size = new Dimension(width, height);
        setPreferredSize(size);
        setBackground(uiController.getDefaultBackgroundcolor());
        buildCartEntry();
        ensureProductPageExistange();
        addActionListener(e -> uiController.setMainWindowContent(productPage));
    }

    /**
     * Product Page needs to be generated druing initialization of uiController, because obviously it needs the uiController reference and before
     * that it doesn't exist.
     */
    public void ensureProductPageExistange() {
        if (product.getProductPage() == null) {
            productPage = new ProductPage(uiController, product);
            product.setPrdoductPage(productPage);
        } else {
            productPage = product.getProductPage();
        }
    }

    /**
     * builds the Cart Entry/ puts the two sides together
     */
    public void buildCartEntry() {
        this.setLayout(new BorderLayout());
        this.add(buildPictureTitleSpace(), BorderLayout.WEST);
        this.add(buildPriceOrderEditSpace(), BorderLayout.EAST);
    }

    /**
     * builds the picture and the title and the desctibtion of this product
     * @return
     */
    public JPanel buildPictureTitleSpace() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 70));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;

        // Image  ========================
        int imageWidth = 60;
        int imageHeight = (int) size.getHeight();
        ImageIcon imageIcon = product.getImage(1);
        //scale image
        Image rawImage = imageIcon.getImage();
        Image scaledImage = rawImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
        // put image into Icon and then into Label
        imageIcon.setImage(scaledImage);
        JLabel image = new JLabel(imageIcon);
        // add imageLabel to panel

        panel.add(image, c);
        c.gridx++;
        panel.add(
                new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(10, (int) size.getHeight())),
                c);

        // Title  ========================
        String title = product.getTitleString();
        JLabel titleLabel = new JLabel(title);
        //set Font
        Font titleFont = uiController.getDefaultFont().deriveFont(Font.BOLD, 13);
        titleLabel.setFont(titleFont);
        // add to panel
        c.gridx++;
        c.gridwidth = 4;
        panel.add(titleLabel, c);
        return panel;
    }

    /**
     * builds the Price something to change the amount of one order and the opportunity to cancel the entire order
     * @return
     */
    public JPanel buildPriceOrderEditSpace() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(300, 70));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weightx = 0.35;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.VERTICAL;

        // Price  ===========================
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BorderLayout());
        String price = product.getPriceString();
        JLabel priceLabel = new JLabel(price);
        pricePanel.setPreferredSize(new Dimension(90, 60));
        //set Font
        Font priceFont = uiController.getDefaultFont().deriveFont(Font.BOLD, 20);
        priceLabel.setFont(priceFont);
        // add to panel
        pricePanel.add(priceLabel, BorderLayout.WEST);
        panel.add(pricePanel, c);
        // more space =======================
        c.gridx++;
        c.weightx = 0.05;
        panel.add(
                new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(10, (int) size.getHeight())),
                c);

        // numberEditor  ====================
        JSpinner spinner = new JSpinner();
        SpinnerNumberModel sm = new SpinnerNumberModel();
        sm.setMinimum(1);
        spinner.setModel(sm);
        spinner.setValue(order.getAmount());
        spinner.setPreferredSize(new Dimension(50, 40));
        spinner.addChangeListener(e -> {
            order.orderAmountChanged(this, (int) spinner.getValue());
            if (!controller.getModel().canAffort(product.getPrice() * ((int) spinner.getValue() + 1))) {
                spinner.setUI(new BasicSpinnerUI() {
                    protected Component createNextButton() {
                        return null;
                    }
                });
                spinner.repaint();
                spinner.revalidate();
            } else {
                spinner.setUI(new BasicSpinnerUI());
                spinner.repaint();
                spinner.revalidate();
            }
        });
        c.weightx = 0.2;
        panel.add(spinner, c);

        // more space =======================
        c.gridx++;
        c.weightx = 0.05;
        panel.add(
                new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(5, (int) size.getHeight())),
                c);

        // Delete   ===========================
        String redCrossPath = "resources/GUI_images/CrossTransparent.png";
        ImageButton button = new ImageButton(redCrossPath, e -> order.deleteOrder());
        button.setBorder(BorderFactory.createEmptyBorder());
        button.resizeImageButton(new Dimension(40, 40));
        button.setBackground(uiController.getDefaultBackgroundcolor());
        c.gridx++;
        c.weightx = 0.3;
        panel.add(button, c);

        // Extra Space for scrollbar   ========
        c.gridx++;
        c.weightx = 0.05;
        panel.add(
                new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(4, (int) size.getHeight())),
                c);
        return panel;
    }

    public Order getOrder() {
        return order;
    }
}
