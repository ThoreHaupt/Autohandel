package lib.uiComponents;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import Controller.Controller;
import GUI.UIController;
import LocalizationLogic.LocalizationController;

/**
 * MultiLanguageTextFiled
 */
public class MLTextField extends JTextField {
    UIController uiController;
    LocalizationController lc;
    String text;
    boolean update;

    public MLTextField(UIController uiController, String text) {
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
        if (!update)
            return;
        this.setText(text);
        revalidate();
    }

    public void setAutoLanguageAdaption(boolean b) {
        update = b;
    }

    public void setDocumentFilter(DocumentFilter filter) {
        ((PlainDocument) getDocument()).setDocumentFilter(filter);
    }

}
