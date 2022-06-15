package lib.technicalComponents;

import GUI.UIController;

public class LocalizedString {
    String value;
    UIController uiController;

    public LocalizedString(UIController uiController, String value) {
        this.value = uiController.getTransatedString(value);
        this.uiController = uiController;
        uiController.getController().getLocalizationController().addLanguageChangeListener(e -> updateValue());
    }

    public void updateValue() {
        value = uiController.getTransatedString(value);
    }

    public String toString() {
        return value;
    }
}
