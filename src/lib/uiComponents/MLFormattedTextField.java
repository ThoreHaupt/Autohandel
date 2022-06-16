package lib.uiComponents;

import java.text.Format;
import java.text.NumberFormat;
import java.util.function.Predicate;

import javax.swing.*;

import Controller.Controller;
import GUI.UIController;
import LocalizationLogic.LocalizationController;
import lib.uiComponents.technicalUIComponents.CustomTextComponent;

/**
 * MultiLanguageFormattedTextFiled Numbers only
 */
public class MLFormattedTextField extends JFormattedTextField implements CustomTextComponent {
    UIController uiController;
    LocalizationController lc;
    String text;
    boolean update;

    public MLFormattedTextField(UIController uiController, String text, NumberFormat allowed) {
        super(allowed);
        setValue(12345.98D);
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
        if (!update)
            return;
        this.setText(text);
        revalidate();
    }

    public void setAutoLanguageAdaption(boolean b) {
        update = b;
    }

}
