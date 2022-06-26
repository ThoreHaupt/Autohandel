package lib.uiComponents;

import javax.swing.*;

import Controller.Controller;
import GUI.UIController;
import LocalizationLogic.LocalizationController;
import lib.uiComponents.technicalUIComponents.CustomTextComponent;

/**
 * MultiLanguageTextFiled
 */
public class MLComboBox extends JComboBox<String> implements CustomTextComponent {
    UIController uiController;
    LocalizationController lc;
    boolean update;

    public MLComboBox(UIController uiController) {
        this(uiController, new String[] {});
    }

    public MLComboBox(UIController uiController, String[] entries) {
        super(entries);
        this.uiController = uiController;
        Controller controller = uiController.getController();
        this.lc = controller.getLocalizationController();
        lc.addLanguageChangeListener(e -> updateText());
    }

    public void updateText() {
        if (!update)
            return;

        String[] updatedStrings = new String[getModel().getSize()];

        for (int i = 0; i < getModel().getSize(); i++) {
            updatedStrings[i] = lc.s(getModel().getElementAt(i));
        }
        removeAllItems();
        for (int i = 0; i < getModel().getSize(); i++) {
            addItem(updatedStrings[i]);
        }
    }

    public void setAutoLanguageAdaption(boolean b) {
        update = b;
    }

    public String getText() {
        return getItemAt(getSelectedIndex());
    }

    @Override
    public void resetValue() {
        setSelectedIndex(0);
    }

}
