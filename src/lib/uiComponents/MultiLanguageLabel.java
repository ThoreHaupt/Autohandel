package lib.uiComponents;

import javax.swing.*;

import GUI.UIController;
import LocalizationLogic.LocalizationController;

import java.awt.*;

public class MultiLanguageLabel extends JLabel {
    UIController uiController;
    LocalizationController lc;
    String text;

    public MultiLanguageLabel(UIController uiController, String text) {
        this.uiController = uiController;
        this.text = text;
        this.lc = uiController.getController().getLanguageController();
        this.setText(lc.s(text));
        lc.addLanguageChangeListener(e -> setText(text));
    }

    @Override
    public void setText(String text) {
        this.text = text;
        super.setText(lc.s(text));
    }

}
