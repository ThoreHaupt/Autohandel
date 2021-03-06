package GUI.shopPage;

import javax.swing.*;

import Controller.Controller;
import GUI.UIController;

import java.awt.*;
import java.awt.event.*;

import Model.ModelComponentes.Product;
import lib.uiComponents.MLButton;
import lib.uiComponents.MLLabel;
import lib.uiComponents.rigitFreeSpace;

public class ShopEntryContent extends JPanel {
    private Product product;
    private UIController uiController;
    private Controller controller;
    private Window window;
    private int optimalWidth;
    private int usedWidth = 0;

    private boolean isGallery;

    private JLabel priceLabel;
    private JPanel buffer;

    /**
     * Constructor
     * @param uiController the UIController
     * @param product the product
     * @param isGalary weather or not this Entry is for the galary or for the "detailed" (theoratically) Product Page
     */
    public ShopEntryContent(UIController uiController, Product product, boolean isGalary) {
        this.product = product;
        this.uiController = uiController;
        this.controller = uiController.getController();
        window = uiController.getWindow();
        this.isGallery = isGalary;
        this.setBackground(new Color(255, 255, 255));
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        if (isGalary) {
            optimalWidth = uiController.getStandartPanel(UIController.MAINSTORE_PAGE).getWidth() - 60;
            this.add(buildShopGalleryEntry());
        }

        if (!isGalary)
            this.add(buildCarPage());

    }

    /**
     * builds the Information page with the button, which you get, when you press on the product in the overview
     * @return
     */
    private Component buildCarPage() {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BorderLayout());
        informationPanel.add(buildTitle(45), BorderLayout.NORTH);
        informationPanel.add(buildPriceArea(true), BorderLayout.EAST);
        informationPanel.add(buildInformationText(false), BorderLayout.CENTER);

        JPanel westInformationPanel = new JPanel();
        westInformationPanel.setLayout(new BorderLayout());
        westInformationPanel.add(buildImageArea(new Dimension(450, 300)), BorderLayout.NORTH);
        informationPanel.add(westInformationPanel, BorderLayout.WEST);
        return informationPanel;
    }

    /**
     * builds the product Entry for when the Product is displayed inside the Shop page 
     * @return
     */
    public JPanel buildShopGalleryEntry() {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BorderLayout());
        informationPanel.add(buildTitle(30), BorderLayout.NORTH);
        informationPanel.add(buildPriceArea(false), BorderLayout.EAST);
        informationPanel.add(buildImageArea(new Dimension(240, 160)), BorderLayout.WEST);
        informationPanel.add(buildInformationText(true), BorderLayout.CENTER);
        return informationPanel;
    }

    /**
     * Builds the Image for this Product
     * @param dimension
     * @return
     */
    private JPanel buildImageArea(Dimension dimension) {
        JPanel imagePanelSection = new JPanel();
        imagePanelSection.setMinimumSize(dimension);
        usedWidth += dimension.getWidth();
        ImageIcon imageIcon = product.getImage(0);

        Image rawImage = imageIcon.getImage();
        Image scaledImage = rawImage.getScaledInstance((int) dimension.getWidth(), (int) dimension.getHeight(),
                Image.SCALE_DEFAULT);
        imageIcon.setImage(scaledImage);
        JLabel image = new JLabel(imageIcon);
        imagePanelSection.add(image);
        return imagePanelSection;
    }

    /**
     * Builds the title
     * @param size
     * @return
     */
    public JPanel buildTitle(int size) {
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        JLabel title = new JLabel(product.getDescribtionTitle());
        Font titleFont = new Font(uiController.getDefaultFont().getName(), Font.BOLD, size);
        title.setFont(titleFont);
        header.add(title, BorderLayout.WEST);
        return header;
    }

    /**
     * Builds the middle part, that has the information text and so on
     * @param b
     * @return
     */
    public JPanel buildInformationText(boolean b) {
        JPanel textPanel = new JPanel();
        String textString = b ? product.getShortInformationText() : product.getShortInformationText();

        JLabel text = new JLabel(textString);
        usedWidth += text.getMinimumSize().getWidth();
        textPanel.add(text);

        JPanel back = new JPanel();
        this.buffer = new JPanel();
        //buffer.setPreferredSize(new DimensionUIResource(optimalWidth - usedWidth, 100));
        back.setLayout(new BorderLayout());
        back.add(textPanel, BorderLayout.WEST);
        back.add(buffer, BorderLayout.EAST);
        return back;
    }

    /**
     * builds the right side of the shopping page which displays the price and the add to cart button
     * @param withButton
     * @return
     */
    public JPanel buildPriceArea(boolean withButton) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 100));
        usedWidth += 200;

        if (!product.hasPrice()) {
            return panel;
        }

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.9;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;

        // build the price Display Area
        priceLabel = new JLabel(product.getPriceString());
        priceLabel.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 20));
        if (product.hasPrice())
            priceLabel.setForeground(uiController.getPriceBasedOnBudgetColor(product.getPrice()));
        // reset Color, when the filter changes -> eg new Spending maximum, or when the user buys something, so the budget changes
        if (product.hasPrice())
            uiController.getController().addChangeToCartListener(
                    e -> priceLabel.setForeground(uiController.getPriceBasedOnBudgetColor(product.getPrice())));
        if (product.hasPrice())
            uiController.getController().getModel().addFilterChangeListener(
                    e -> {
                        priceLabel.setForeground(uiController.getPriceBasedOnBudgetColor(product.getPrice()));
                    });
        panel.add(priceLabel, c);

        c.gridy++;

        panel.add(new rigitFreeSpace(null, new Dimension(300, withButton ? 10 : 10)), c);

        c.gridy++;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.CENTER;

        // the incl Value added Tax label
        MLLabel otherText = new MLLabel(this.uiController, "incl MwStr.");
        otherText.setPreferredSize(new Dimension(30, 20));
        panel.add(otherText, c);

        c.gridy++;

        //build add to cart Area
        if (withButton) {
            c.gridx = 1;
            c.gridwidth = 1;
            MLButton addToCart = new MLButton(uiController, "AddToCart");
            JSpinner spinner = new JSpinner();
            addToCart.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.addToOrder(product, (int) spinner.getValue());
                }

            });

            controller.getModel().addChangeToCartListener(e -> {
                if (!controller.getModel().canAffort(product.getPrice() * (int) spinner.getValue())) {
                    addToCart.setEnabled(false);
                } else {
                    addToCart.setEnabled(true);
                }
            });

            controller.getModel().addFilterChangeListener(e -> {
                if (!controller.getModel().canAffort(product.getPrice() * (int) spinner.getValue())) {
                    addToCart.setEnabled(false);
                } else {
                    addToCart.setEnabled(true);
                }
            });

            addToCart.setBackground(uiController.getDefaultAccentColor());
            addToCart.setForeground(new Color(255, 255, 255));
            addToCart.setPreferredSize(new Dimension(40, 30));
            panel.add(addToCart, c);

            c.gridx = 0;

            SpinnerNumberModel sm = new SpinnerNumberModel();
            sm.setMinimum(1);
            spinner.setModel(sm);
            spinner.setValue(1);
            spinner.setPreferredSize(new Dimension(40, 30));

            spinner.addChangeListener(e -> {
                if (!controller.getModel().canAffort(product.getPrice() * (int) spinner.getValue())) {
                    addToCart.setEnabled(false);
                } else {
                    addToCart.setEnabled(true);
                }
            });

            panel.add(spinner, c);
        }
        return panel;

    }

    /**
     * dont need that no
     * @param newOptimalSize
     */
    public void revalidateBufferSize(int newOptimalSize) {
        //buffer.setPreferredSize(new DimensionUIResource(optimalWidth - usedWidth, 100));
        //revalidate();
        //System.out.println("hi");
    }

}
