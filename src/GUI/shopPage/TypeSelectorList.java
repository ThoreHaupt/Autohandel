package GUI.shopPage;

import java.awt.Font;
import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import GUI.UIController;
import Model.ModelComponentes.TypeMutation;
import Model.UserComponentes.Filter;
import lib.uiComponents.MLLabel;

public class TypeSelectorList extends JPanel {
    UIController uiController;

    public TypeSelectorList(UIController uiController, Filter filter, String type) {
        this.uiController = uiController;

        this.setLayout(new BorderLayout());
        //Title
        JPanel titlePanel = buildTypeTitle(filter.getTitleByType(type));
        add(titlePanel, BorderLayout.NORTH);
        // BrandPanel:
        JPanel brandPanel = buildTypeSelectionPanel(filter.getTypeMutations(type));
        add(brandPanel, BorderLayout.CENTER);
    }

    private JPanel buildTypeTitle(String titleString) {
        JPanel panel = new JPanel();
        MLLabel title = new MLLabel(uiController, titleString);
        title.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 20));
        return panel;
    }

    private JPanel buildTypeSelectionPanel(TypeMutation[] types) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (int i = 0; i < types.length; i++) {
            JCheckBox box = new JCheckBox(types[i].getName());
            box.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 10));
            TypeMutation currentType = types[i];
            box.addChangeListener(e -> currentType.setCurrentStatus(((JCheckBox) e.getSource()).isSelected()));
        }

        return panel;
    }
}
