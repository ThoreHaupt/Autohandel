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
        this.value = 0;
        this.maxText = new LocalizedString(uiController, maxText);
        this.uiController = uiController;
        this.changeOnMax = changeOnMax;
        this.buildMaxEventResponse();
        setDocumentFilter(filter);

        setCorrectRange();
        handlePainting();

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
                    setText(String.valueOf(changeOnMax ? maxValue : minValue));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                setCorrectRange();
                handlePainting();
                if (isAtMaxPoint)
                    SwingUtilities.invokeLater(performAction);
            }

        });
        uiController.getController().getLocalizationController().addLanguageChangeListener(e -> {
            maxText.updateValue();
            handlePainting();
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
        handlePainting();
    }

    public void setCorrectRange() {
        if (value <= minValue) {
            System.out.println("low");
            value = minValue;
            isAtMaxPoint = changeOnMax ? false : true;
            System.out.println(isAtMaxPoint);
        } else if (value >= maxValue) {
            System.out.println("high");
            value = maxValue;
            isAtMaxPoint = changeOnMax ? false : true;
            System.out.println(isAtMaxPoint);
        } else {
            isAtMaxPoint = false;
        }

    }

    public int getValue() {
        return value;
    }

    private void handlePainting() {
        if (isAtMaxPoint) {
            setTextBypassed(maxText.toString());
        } else {
            setText(String.valueOf(value));
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
        handlePainting();
    }
}
