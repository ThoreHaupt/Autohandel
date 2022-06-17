package lib.uiComponents;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import GUI.UIController;
import lib.uiComponents.technicalUIComponents.CustomTextComponent;

import java.awt.*;

public class PasswordFieldWithDescribtion extends JPanel implements CustomTextComponent {
    MLLabel label;
    JPasswordField passwordField;

    public PasswordFieldWithDescribtion(UIController c, String describtionText, String textfieldText,
            Dimension textFieldSize) {

        label = new MLLabel(c, describtionText);
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(textFieldSize);
        this.setLayout(new BorderLayout());
        add(label, BorderLayout.WEST);
        add(passwordField, BorderLayout.EAST);
    }

    public String getText() {
        return new String(passwordField.getPassword());
    }

    @Override
    public void resetValue() {
        passwordField.setText("");

    }
}
