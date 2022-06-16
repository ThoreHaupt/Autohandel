package GUI.shopPage;

import javax.swing.*;

import GUI.UIController;

import java.awt.*;
import java.awt.event.*;

import Model.ModelComponentes.Car;
import Model.UserComponentes.Order;
import lib.uiComponents.MLButton;
import lib.uiComponents.MLLabel;
import lib.uiComponents.rigitFreeSpace;

public class ShopEntryContent extends JPanel {
    private Car car;
    private UIController uiController;
    private Window window;
    private int optimalWidth;
    private int usedWidth = 0;

    private boolean isGallery;

    private JLabel priceLabel;
    private JPanel buffer;

    public ShopEntryContent(UIController uiController, Car model, boolean isGalary) {
        this.car = model;
        this.uiController = uiController;
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

    public JPanel buildShopGalleryEntry() {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BorderLayout());
        informationPanel.add(buildTitle(30), BorderLayout.NORTH);
        informationPanel.add(buildPriceArea(false), BorderLayout.EAST);
        informationPanel.add(buildImageArea(new Dimension(240, 160)), BorderLayout.WEST);
        informationPanel.add(buildInformationText(true), BorderLayout.CENTER);
        return informationPanel;
    }

    private JPanel buildImageArea(Dimension dimension) {
        JPanel imagePanelSection = new JPanel();
        imagePanelSection.setMinimumSize(dimension);
        usedWidth += dimension.getWidth();
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

    public JPanel buildPriceArea(boolean withButton) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(150, 200));
        usedWidth += 150;
        panel.setLayout(new BorderLayout());
        priceLabel = new JLabel(car.getPriceString() + " €");
        priceLabel.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 20));
        panel.add(new rigitFreeSpace(null, new Dimension(150, withButton ? 50 : 20)), BorderLayout.NORTH);
        MLLabel otherText = new MLLabel(this.uiController, "incl MwStr.");

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BorderLayout());
        JPanel subsubPanel = new JPanel();
        subsubPanel.setLayout(new BorderLayout());

        if (withButton) {
            MLButton addToCart = new MLButton(uiController, "Configurator");
            addToCart.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    uiController.getController().addToOrder(car, 1);
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
            priceLabel.setForeground(uiController.getAffortableColor());
        } else {
            priceLabel.setForeground(uiController.getNotAffortableColor());
        }
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
