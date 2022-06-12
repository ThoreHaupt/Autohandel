package GUI.shopPage;

import javax.swing.*;
import javax.swing.border.Border;

import GUI.UIController;

import java.awt.*;

import Model.ModelComponentes.CarOption;
import lib.uiComponents.rigitFreeSpace;

public class ShopEntryContent extends JButton {
    CarOption model;
    UIController uiController;

    public ShopEntryContent(UIController uiController, CarOption model) {
        this.model = model;
        this.uiController = uiController;
        this.setBackground(uiController.getDefaultBackgroundcolor());
        this.add(buildShopEntry());
    }

    public JPanel buildShopEntry() {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BorderLayout());
        informationPanel.add(buildTitle(), BorderLayout.NORTH);
        informationPanel.add(buildInformationText(), BorderLayout.CENTER);
        informationPanel.add(buildPriceArea(), BorderLayout.EAST);
        informationPanel.add(buildImageArea(), BorderLayout.WEST);
        return informationPanel;
    }

    private JPanel buildImageArea() {
        JPanel imagePanelSection = new JPanel();
        ImageIcon imageIcon = new ImageIcon(model.getImagePath());
        JLabel image = new JLabel(imageIcon);
        imagePanelSection.add(image);
        return imagePanelSection;
    }

    public JPanel buildTitle() {
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        JLabel title = new JLabel("model.getDescribtionTitle()");
        Font titleFont = new Font(uiController.getDefaultFont().getName(), Font.BOLD, 30);
        // JLabel title = new JLabel(model.getDescribtionTitle());
        title.setFont(titleFont);
        header.add(title, BorderLayout.WEST);
        return header;
    }

    public JPanel buildInformationText() {
        JPanel textPanel = new JPanel();
        String textString = model.getInformationText();
        JLabel text = new JLabel(textString);
        textPanel.add(text);
        return textPanel;
    }

    public JPanel buildPriceArea() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel price = new JLabel(model.getPriceString() + " €");
        price.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 20));
        panel.add(new rigitFreeSpace(null, new Dimension(150, 20)), BorderLayout.NORTH);
        JLabel otherText = new JLabel(uiController.getController().lc.s("incl MwStr."));

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BorderLayout());
        subPanel.add(price, BorderLayout.NORTH);
        subPanel.add(otherText, BorderLayout.CENTER);
        panel.add(subPanel);
        return panel;
    }
}
