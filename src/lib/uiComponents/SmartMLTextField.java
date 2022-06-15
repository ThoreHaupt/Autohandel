package lib.uiComponents;

import GUI.UIController;
import lib.technicalComponents.DocumentNumberFilter;
import lib.technicalComponents.LocalizedString;

import javax.swing.text.PlainDocument;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DocumentFilter;

import java.awt.event.*;

public class SmartMLTextField extends JTextField {
    UIController uiController;

    int maxValue;
    int minValue;
    int value;
    boolean changeOnMax;
    LocalizedString maxText;

    boolean isAtMaxPoint = false;

    DocumentFilter defaultFilter;
    DocumentFilter filter;

    public SmartMLTextField(UIController uiController, String maxText, int minValue, int maxValue,
            boolean changeOnMax) {
        filter = new DocumentNumberFilter();
        defaultFilter = new DocumentFilter();
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.value = minValue;
        this.maxText = new LocalizedString(uiController, maxText);
        this.uiController = uiController;
        this.changeOnMax = changeOnMax;
        this.buildMaxEventResponse();
        setDocumentFilter(filter);
        handleAtMax();
        uiController.getController().getLocalizationController().addLanguageChangeListener(e -> updateLangugage());
    }

    public void setMaximum(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinimum(int minValue) {
        this.minValue = minValue;
    }

    @SuppressWarnings("unused")
    private void changeUsedFilter(DocumentFilter filter) {
        this.filter = filter;
        this.setDocumentFilter(filter);
    }

    private void buildMaxEventResponse() {
        addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (isAtMaxPoint) {
                    setTextBypassed((changeOnMax ? maxValue : minValue) + "");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                setCorrectRange();
                if (isAtMaxPoint) {
                    setTextBypassed(maxText.toString());
                }
                SwingUtilities.invokeLater(performAction);
            }

        });
        uiController.getController().getLocalizationController().addLanguageChangeListener(e -> {
            maxText.updateValue();
            handleAtMax();
        });

        getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {

                //SwingUtilities.invokeLater(performAction);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //SwingUtilities.invokeLater(performAction);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //SwingUtilities.invokeLater(performAction);
            }
        });
    }

    private void setDocumentFilter(DocumentFilter filter) {
        ((PlainDocument) getDocument()).setDocumentFilter(filter);
    }

    public void setTextBypassed(String s) {
        setDocumentFilter(defaultFilter);
        super.setText(s);
        setDocumentFilter(filter);
    }

    public void setValue(int newVal) {
        value = newVal;
        setCorrectRange();
        setText(String.valueOf(newVal));
        handleAtMax();
        SwingUtilities.invokeLater(performAction);
    }

    public void setCorrectRange() {
        if (value <= minValue) {
            value = minValue;
            isAtMaxPoint = true;
        } else if (value >= maxValue) {
            value = maxValue;
            isAtMaxPoint = false;
        }
        isAtMaxPoint = false;
    }

    public int getValue() {
        return value;
    }

    private void handleAtMax() {
        if (isAtMaxPoint) {
            setTextBypassed(maxText.toString());
        } else {
            isAtMaxPoint = false;
        }
    }

    Runnable performAction = new Runnable() {
        @Override
        public void run() {
            fireActionPerformed();
        }
    };

    void updateLangugage() {
        maxText.updateValue();
        if (isAtMaxPoint) {
            setText(maxText.toString());
        }
    }
}
