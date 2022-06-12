package GUI.shopPage;

import javax.swing.*;
import javax.swing.border.Border;

import GUI.UIController;

import java.awt.*;
import java.awt.event.*;

import Model.ModelComponentes.CarOption;
import lib.uiComponents.rigitFreeSpace;

public class ShopEntryContent extends JPanel {
    private CarOption car;
    private UIController uiController;
    private boolean isGallery;

    public ShopEntryContent(UIController uiController, CarOption model, boolean isGalary) {
        this.car = model;
        this.uiController = uiController;
        this.isGallery = isGalary;
        this.setBackground(uiController.getDefaultBackgroundcolor());
        if (isGalary)
            this.add(buildShopGalleryEntry());

        if (!isGalary)
            this.add(buildCarPage());
    }

    private Component buildCarPage() {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BorderLayout());
        informationPanel.add(buildTitle(45), BorderLayout.NORTH);
        informationPanel.add(buildInformationText(false), BorderLayout.CENTER);
        informationPanel.add(buildPriceArea(true), BorderLayout.EAST);
        informationPanel.add(buildImageArea(new Dimension()), BorderLayout.WEST);
        return informationPanel;
    }

    public JPanel buildShopGalleryEntry() {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BorderLayout());
        informationPanel.add(buildTitle(30), BorderLayout.NORTH);
        informationPanel.add(buildInformationText(true), BorderLayout.CENTER);
        informationPanel.add(buildPriceArea(false), BorderLayout.EAST);
        informationPanel.add(buildImageArea(new Dimension()), BorderLayout.WEST);
        return informationPanel;
    }

    private JPanel buildImageArea(Dimension dimension) {
        JPanel imagePanelSection = new JPanel();
        ImageIcon imageIcon = new ImageIcon(car.getImagePath());
        JLabel image = new JLabel(imageIcon);
        imagePanelSection.add(image);
        return imagePanelSection;
    }

    public JPanel buildTitle(int size) {
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        JLabel title = new JLabel("model.getDescribtionTitle()");
        Font titleFont = new Font(uiController.getDefaultFont().getName(), Font.BOLD, size);
        // JLabel title = new JLabel(model.getDescribtionTitle());
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
        panel.setLayout(new BorderLayout());
        JLabel price = new JLabel(car.getPriceString() + " €");
        price.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 20));
        panel.add(new rigitFreeSpace(null, new Dimension(150, 20)), BorderLayout.NORTH);
        JLabel otherText = new JLabel(uiController.getController().lc.s("incl MwStr."));

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BorderLayout());
        JPanel subsubPanel = new JPanel();
        subsubPanel.setLayout(new BorderLayout());

        if (withButton) {
            JButton addToCart = new JButton();
            addToCart.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    uiController.getController().addToCart(car);
                }

            });

            addToCart.setBackground(uiController.getDefaultAccentColor());

        }

        subsubPanel.add(price, BorderLayout.NORTH);
        subsubPanel.add(otherText, BorderLayout.SOUTH);
        subPanel.add(subsubPanel, BorderLayout.NORTH);
        panel.add(subPanel);
        return panel;
    }
}
