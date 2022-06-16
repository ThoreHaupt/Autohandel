package GUI.shopPage;

import java.awt.Font;
import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.UIController;
import Model.UserComponentes.Filter;
import Model.UserComponentes.UserBrandSettings;

public class BrandsSelectorList extends JPanel {
    UIController uiController;

    public BrandsSelectorList(UIController uiController, Filter filter) {
        this.uiController = uiController;

        this.setLayout(new BorderLayout());
        //Title

        // BrandPanel:
        JPanel brandPanel = buildBrandPanel(filter.getBrands());
        add(brandPanel, BorderLayout.CENTER);

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
