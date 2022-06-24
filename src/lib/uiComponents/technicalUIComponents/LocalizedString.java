package lib.uiComponents.technicalUIComponents;

import GUI.UIController;

public class LocalizedString {
    String value;
    String translatedValue;
    UIController uiController;

    public LocalizedString(UIController uiController, String value) {
        this.value = value;
        this.translatedValue = uiController.getTranslatedString(value);
        this.uiController = uiController;
        uiController.getController().getLocalizationController().addLanguageChangeListener(e -> updateValue());
    }

    public void updateValue() {
        translatedValue = uiController.getTranslatedString(value);
    }

    public String toString() {
        return translatedValue;
    }
}
