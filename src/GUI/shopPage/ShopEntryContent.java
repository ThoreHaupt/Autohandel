package GUI.shopPage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;

import GUI.UIController;

import java.awt.*;
import java.awt.event.*;

import Model.ModelComponentes.Car;
import lib.uiComponents.rigitFreeSpace;

public class ShopEntryContent extends JPanel {
    private Car car;
    private UIController uiController;
    private Window window;
    private boolean isGallery;

    private JLabel priceLabel;

    public ShopEntryContent(UIController uiController, Car model, boolean isGalary) {
        this.car = model;
        this.uiController = uiController;
        window = uiController.getWindow();
        this.isGallery = isGalary;
        // this.setBackground(uiController.getDefaultBackgroundcolor());
        this.setOpaque(false);
        if (isGalary)
            this.add(buildShopGalleryEntry());

        if (!isGalary)
            this.add(buildCarPage());

        uiController.getWindow().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {

                revalidate();
            }
        });
    }

    private Component buildCarPage() {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BorderLayout());
        informationPanel.add(buildTitle(45), BorderLayout.NORTH);
        informationPanel.add(buildInformationText(false), BorderLayout.CENTER);
        informationPanel.add(buildPriceArea(true), BorderLayout.EAST);

        JPanel westInformationPanel = new JPanel();
        westInformationPanel.setLayout(new BorderLayout());
        westInformationPanel.add(buildImageArea(new Dimension(450, 300)), BorderLayout.NORTH);
        informationPanel.add(westInformationPanel, BorderLayout.WEST);
        return informationPanel;
    }

    public JPanel buildShopGalleryEntry() {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BorderLayout());
        informationPanel.add(buildTitle(30), BorderLayout.NORTH);
        informationPanel.add(buildInformationText(true), BorderLayout.CENTER);
        informationPanel.add(buildPriceArea(false), BorderLayout.EAST);
        informationPanel.add(buildImageArea(new Dimension(240, 160)), BorderLayout.WEST);
        return informationPanel;
    }

    private JPanel buildImageArea(Dimension dimension) {
        JPanel imagePanelSection = new JPanel();
        imagePanelSection.setPreferredSize(dimension);
        ImageIcon imageIcon = new ImageIcon(car.getImagePath());

        Image rawImage = imageIcon.getImage();
        Image scaledImage = rawImage.getScaledInstance((int) dimension.getWidth(), (int) dimension.getHeight(),
                Image.SCALE_FAST);
        imageIcon.setImage(scaledImage);
        JLabel image = new JLabel(imageIcon);
        imagePanelSection.add(image);
        return imagePanelSection;
    }

    public JPanel buildTitle(int size) {
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        JLabel title = new JLabel(car.getDescribtionTitle());
        Font titleFont = new Font(uiController.getDefaultFont().getName(), Font.BOLD, size);
        title.setFont(titleFont);
        header.add(title, BorderLayout.WEST);
        return header;
    }

    public JPanel buildInformationText(boolean b) {
        JPanel textPanel = new JPanel();
        String textString = b ? car.getShortInformationText() : car.getExtensiveInformationText();
        JLabel text = new JLabel(textString);
        textPanel.add(text);

        return textPanel;
    }

    public JPanel buildPriceArea(boolean withButton) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(150, 200));
        panel.setLayout(new BorderLayout());
        priceLabel = new JLabel(car.getPriceString() + " €");
        priceLabel.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 20));
        panel.add(new rigitFreeSpace(null, new Dimension(150, withButton ? 50 : 20)), BorderLayout.NORTH);
        JLabel otherText = new JLabel(uiController.getController().lc.s("incl MwStr."));

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BorderLayout());
        JPanel subsubPanel = new JPanel();
        subsubPanel.setLayout(new BorderLayout());

        if (withButton) {
            JButton addToCart = new JButton(uiController.getController().lc.s("add to Cart"));
            addToCart.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    uiController.getController().addToCart(car);
                }

            });

            addToCart.setBackground(uiController.getDefaultAccentColor());
            addToCart.setForeground(new Color(255, 255, 255));
            addToCart.setPreferredSize(new Dimension(50, 30));
            subsubPanel.add(addToCart, BorderLayout.SOUTH);
        }

        subsubPanel.add(priceLabel, BorderLayout.NORTH);
        subsubPanel.add(otherText, BorderLayout.CENTER);
        subPanel.add(subsubPanel, BorderLayout.NORTH);
        panel.add(subPanel);
        return panel;
    }

    public void setCanAffort(boolean affortable) {
        if (affortable) {
            priceLabel.setForeground(new Color(30, 245, 38));
        } else {
            priceLabel.setForeground(new Color(245, 45, 30));
        }
    }

}
