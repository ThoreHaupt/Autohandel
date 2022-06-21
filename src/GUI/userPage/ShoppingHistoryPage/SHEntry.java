package GUI.userPage.ShoppingHistoryPage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import Controller.Controller;
import GUI.UIController;
import GUI.shopPage.ProductPage;
import Model.ModelComponentes.Product;
import Model.UserComponentes.Order;
import lib.uiComponents.ImageButton;
import lib.uiComponents.rigitFreeSpace;

import java.awt.*;

public class SHEntry extends JButton {

    UIController uiController;
    Controller controller;
    Order order;
    Product product;
    Dimension size;
    ProductPage productPage;

    /**
     * @param uiController
     * @param order
     */
    public SHEntry(UIController uiController, Order order, int width) {
        this.uiController = uiController;
        this.controller = uiController.getController();
        this.order = order;
        this.product = order.getProduct();
        size = new Dimension(width, 60);
        setPreferredSize(size);
        setBackground(uiController.getDefaultBackgroundcolor());
        buildSHEntry();
        ensureProductPageExistange();
        addActionListener(e -> uiController.setMainWindowContent(productPage));
    }

    /**
     * Every Product has a product page, but this page can only be created when the uiCOntroller already exits,
     * which happens after the model initializes. Thus the product can't directly initialize the page, but the
     * page will be set by this if it doesn't exist
     * 
     * Because it is referenced here and in the Shopping Page section and only needs to be created once, this method checks,
     * weather it already exists and if that is not the case it will initialize it. 
     * Usually this method should do nothing, just set the instance variable, because the User Page gets initialized
     * after the Shop Page, but it is safer to do the check
     */
    public void ensureProductPageExistange() {
        if (product.getProductPage() == null) {
            productPage = new ProductPage(uiController, product);
            product.setPrdoductPage(productPage);
        } else {
            productPage = product.getProductPage();
        }
    }

    public void buildSHEntry() {
        this.setLayout(new BorderLayout());
        this.add(buildPictureTitleSpace(), BorderLayout.WEST);
        this.add(buildPriceAmountSpace(), BorderLayout.EAST);
    }

    public JPanel buildPictureTitleSpace() {
        JPanel panel = new JPanel();
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
        String imageString = product.getImageString();
        ImageIcon imageIcon = new ImageIcon(imageString);
        //scale image
        Image rawImage = imageIcon.getImage();
        Image scaledImage = rawImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_FAST);
        // put image into Icon and then into Label
        imageIcon.setImage(scaledImage);
        JLabel image = new JLabel(imageIcon);
        // add imageLabel to panel

        panel.add(image, c);
        // Title  ========================
        String title = product.getTitleString();
        JLabel titleLabel = new JLabel(title);
        //set Font
        Font titleFont = uiController.getDefaultFont().deriveFont(Font.BOLD, 13);
        titleLabel.setFont(titleFont);
        // add to panel
        c.gridx = 1;
        c.gridwidth = 4;
        panel.add(titleLabel, c);
        return panel;
    }

    public JPanel buildPriceAmountSpace() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.3;
        c.gridwidth = 1;
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
        panel.add(
                new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(4, (int) size.getHeight())),
                c);

        // numberEditor  ====================

        JLabel amountDisplay = new JLabel(order.getAmount() + "");
        amountDisplay.setPreferredSize(new Dimension(50, 40));
        c.gridx++;
        panel.add(amountDisplay, c);

        // more space =======================
        c.gridx++;
        panel.add(
                new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(4, (int) size.getHeight())),
                c);

        // Extra Space for scrollbar   ===========================
        c.gridx++;
        panel.add(
                new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(4, (int) size.getHeight())),
                c);
        return panel;
    }

    public Order getOrder() {
        return order;
    }
}
