package lib.uiComponents;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Controller.Controller;
import GUI.UIController;
import Model.Model;

import java.awt.*;

public class MLsortSelector extends JPanel {
    UIController uiController;
    Model model;
    String[] options;
    IRButton irButton; // this button selects the direction in which to sort
    MLComboBox comboBox; // for slecting the attribute after which to sort

    Dimension buttonsize = new Dimension(50, 50);

    public MLsortSelector(UIController uiController) {
        this.uiController = uiController;
        Controller controller = uiController.getController();
        this.model = controller.getModel();
        options = model.getSortingOptions();
        buildIRSortSelector();
    }

    public void buildIRSortSelector() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.2;
        panel.add(buildDirectionButton(buttonsize), c);

        c.gridx = 1;
        c.weightx = 0.8;
        panel.add(buildSortQuiteriorSelectionArea(), c);
        this.add(panel);
    }

    public JPanel buildDirectionButton(Dimension n) {
        JPanel panel = new JPanel();
        String[] imagesDirectionSelector = { "resources/GUI_images/SortUpwards.png",
                "resources/GUI_images/SortDownwards.png" }; // up and down
        irButton = new IRButton(imagesDirectionSelector, n);
        irButton.setBorder(BorderFactory.createEmptyBorder());
        irButton.setBackground(uiController.getDefaultBackgroundcolor());
        panel.add(irButton);
        return panel;
    }

    public JPanel buildSortQuiteriorSelectionArea() {
        JPanel panel = new JPanel();
        comboBox = new MLComboBox(uiController, options);
        panel.add(comboBox);
        return panel;
    }

    public int getSlectedSortingOption() {
        return comboBox.getSelectedIndex();
    }

    public int getSelectedSortingDirection() {
        return irButton.getCurrentIndex();
    }
}
