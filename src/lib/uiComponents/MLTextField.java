package lib.uiComponents;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import Controller.Controller;
import GUI.UIController;
import LocalizationLogic.LocalizationController;
import lib.uiComponents.technicalUIComponents.CustomTextComponent;

/**
 * MultiLanguageTextFiled
 */
public class MLTextField extends JTextField implements CustomTextComponent {
    UIController uiController;
    LocalizationController lc;
    String defaultText;
    String currentText;
    boolean update;

    public MLTextField(UIController uiController, String text) {
        super();
        this.uiController = uiController;
        Controller controller = uiController.getController();
        this.lc = controller.getLocalizationController();
        this.defaultText = text;
        this.currentText = defaultText;
        this.setText(text);
        lc.addLanguageChangeListener(e -> updateText());
    }

    @Override
    public void setText(String text) {
        if (lc == null) {
            super.setText(text);
            return;
        }
        this.currentText = text;
        super.setText(lc.s(text));
    }

    public void updateText() {
        if (!update)
            return;
        this.setText(defaultText);
        revalidate();
    }

    public void setChangeAsAction() {
        getDocument().addDocumentListener(new DocumentChangeAsActionListener());
    }

    public void setAutoLanguageAdaption(boolean b) {
        update = b;
    }

    public void setDocumentFilter(DocumentFilter filter) {
        ((PlainDocument) getDocument()).setDocumentFilter(filter);
    }

    @Override
    public void resetValue() {
        setText(defaultText);
    }

    class DocumentChangeAsActionListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            fireActionPerformed();

        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            fireActionPerformed();

        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            fireActionPerformed();
        }
    }
}
