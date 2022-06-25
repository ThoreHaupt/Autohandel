package GUI.shopPage;

import java.awt.Font;
import java.util.ArrayList;
import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import GUI.UIController;
import Model.UserComponentes.Filter;
import Model.UserComponentes.TypeMutation;
import lib.DataStructures.HashMapImplementation.KeyValuePair;
import lib.DataStructures.HashMapImplementation.THashMap;
import lib.uiComponents.MLButton;
import lib.uiComponents.MLLabel;

public class TypeSelectorList extends JPanel {
    UIController uiController;
    Filter filter;
    String type;
    ArrayList<JCheckBox> boxes = new ArrayList<>();

    MLButton changeSelectAll;

    int selectedNum = 0;
    int buttonNum = 0;

    boolean select = false;
    private boolean centralActionFire = false;

    public TypeSelectorList(UIController uiController, Filter filter, String type) {
        this.uiController = uiController;
        this.filter = filter;
        this.type = type;

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
        title.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 15));
        panel.add(title);

        changeSelectAll = new MLButton(uiController, "deselect All");
        changeSelectAll.addActionListener(e -> {
            centralActionFire = true;
            setAll(select);
            centralActionFire = false;
            filter.fireFilterChange();
        });

        panel.add(changeSelectAll);
        return panel;
    }

    private JPanel buildTypeSelectionPanel(THashMap<String, TypeMutation> types) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(types.size() / 2, 2));
        buttonNum = types.size();
        int index = 0;
        for (KeyValuePair<String, TypeMutation> pair : types.asKeyValuePair()) {
            JCheckBox box = new JCheckBox(pair.value().getName());
            boxes.add(box);
            box.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 13));
            TypeMutation currentType = pair.value();
            box.setSelected(pair.value().isSelected());
            box.addChangeListener(e -> {
                if (!centralActionFire)
                    currentType.setCurrentStatus(((JCheckBox) e.getSource()).isSelected());
            });
            box.addChangeListener(e -> {
                if (box.isSelected()) {
                    selectedNum++;
                } else {
                    selectedNum--;
                }
                if (buttonNum / 2 > selectedNum) {
                    select = true;
                    changeSelectAll.setText("select all");
                } else {
                    select = false;
                    changeSelectAll.setText("deselect all");
                }
            });
            panel.add(box, index++);
        }

        return panel;
    }

    private void setAll(boolean state) {
        for (JCheckBox box : boxes) {
            box.setSelected(state);
        }
        select = !state;
        changeSelectAll.setText(select ? "select all" : "deselect all");
        //selectedNum = state ? buttonNum : 0;
    }
}
