package lib.uiComponents;

import javax.swing.JPanel;

import GUI.UIController;
import lib.uiComponents.technicalUIComponents.CustomTextComponent;

import java.awt.*;

/**
 * This is a MLCombobox but it has a describtion on the left side
 */
public class MLComboBoxWithDescribtion extends JPanel implements CustomTextComponent {
    MLLabel label;
    MLComboBox comboBox;

    public MLComboBoxWithDescribtion(UIController uiController, String describtionText, String[] comboBoxText,
            Dimension textFieldSize) {

        label = new MLLabel(uiController, describtionText);
        comboBox = new MLComboBox(uiController, comboBoxText);
        comboBox.setPreferredSize(textFieldSize);

        this.setLayout(new BorderLayout());

        add(label, BorderLayout.WEST);
        add(comboBox, BorderLayout.EAST);

    }

    public int getSelectedIndex() {
        return comboBox.getSelectedIndex();
    }

    public MLComboBox getCombobox() {
        return comboBox;
    }

    @Override
    public String getText() {
        return comboBox.getText();
    }

    @Override
    public void resetValue() {
        comboBox.setSelectedIndex(0);

    }
}
