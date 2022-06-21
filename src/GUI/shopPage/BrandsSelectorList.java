package GUI.shopPage;

import java.awt.Font;
import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import GUI.UIController;
import Model.UserComponentes.Filter;
import Model.UserComponentes.UserBrandSettings;
import lib.uiComponents.MLLabel;

public class BrandsSelectorList extends JPanel {
    UIController uiController;

    public BrandsSelectorList(UIController uiController, Filter filter) {
        this.uiController = uiController;

        this.setLayout(new BorderLayout());
        //Title
        JPanel titlePanel = buildBrandTitle();
        add(titlePanel, BorderLayout.NORTH);
        // BrandPanel:
        JPanel brandPanel = buildBrandPanel(filter.getBrands());
        add(brandPanel, BorderLayout.CENTER);

    }

    private JPanel buildBrandTitle() {
        JPanel panel = new JPanel();
        MLLabel title = new MLLabel(uiController, "Brands:");
        title.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 20));
        return panel;
    }

    private JPanel buildBrandPanel(UserBrandSettings[] brands) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (int i = 0; i < brands.length; i++) {
            JCheckBox box = new JCheckBox(brands[i].getName());
            box.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 10));
            UserBrandSettings currentBrand = brands[i];
            box.addChangeListener(e -> currentBrand.setCurrentStatus(((JCheckBox) e.getSource()).isSelected()));
        }

        return panel;
    }
}
