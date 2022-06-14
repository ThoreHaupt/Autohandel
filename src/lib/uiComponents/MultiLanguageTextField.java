package lib.uiComponents;

import javax.swing.*;

import Controller.Controller;
import GUI.UIController;
import LocalizationLogic.LocalizationController;

import java.awt.*;

public class MultiLanguageTextField extends JTextField {
    UIController uiController;
    LocalizationController lc;
    String text;

    public MultiLanguageTextField(UIController uiController, String text) {
        super();
        this.uiController = uiController;
        Controller controller = uiController.getController();
        this.lc = controller.getLocalizationController();

        this.setText(text);
        lc.addLanguageChangeListener(e -> updateText());
    }

    @Override
    public void setText(String text) {
        if (lc == null) {
            super.setText(text);
            return;
        }
        this.text = text;
        super.setText(lc.s(text));
    }

    public void updateText() {
        this.setText(text);
        revalidate();
    }

}
