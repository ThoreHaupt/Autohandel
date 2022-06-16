package lib.uiComponents;

import javax.swing.JPanel;

import GUI.UIController;
import lib.uiComponents.technicalUIComponents.CustomTextComponent;

import java.awt.*;

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

    public MLComboBox getCombobox() {
        return comboBox;
    }

    @Override
    public String getText() {
        return comboBox.getText();
    }
}
