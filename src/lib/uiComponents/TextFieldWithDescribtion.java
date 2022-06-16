package lib.uiComponents;

import javax.swing.JPanel;

import GUI.UIController;
import java.awt.*;

public class TextFieldWithDescribtion extends JPanel {
    MLLabel label;
    PrewrittenEditableTextField textField;

    public TextFieldWithDescribtion(UIController uiController, String describtionText, String textfieldText,
            Dimension textFieldSize) {

        label = new MLLabel(uiController, describtionText);
        textField = new PrewrittenEditableTextField(uiController, textfieldText);
        textField.setBackground(new Color(255, 0, 0));
        textField.setPreferredSize(textFieldSize);

        this.setLayout(new BorderLayout());

        add(label, BorderLayout.WEST);
        add(textField, BorderLayout.EAST);

    }

    public String getText() {
        return textField.getText();
    }
}
