package lib.uiComponents;

import GUI.UIController;
import lib.uiComponents.technicalUIComponents.DocumentNumberFilter;
import lib.uiComponents.technicalUIComponents.LocalizedString;

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
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.value = 0;
        this.maxText = new LocalizedString(uiController, maxText);
        this.uiController = uiController;
        this.changeOnMax = changeOnMax;
        this.buildMaxEventResponse();

        //setTextDisplay correctly
        setCorrectRange();
        handlePainting();

        //Filter
        filter = new DocumentNumberFilter();
        defaultFilter = new DocumentFilter();
        setDocumentFilter(filter);

        // Language Update
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
                if (!isAtMaxPoint) {
                    value = Integer.parseInt(getText());
                }
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
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (!isFocusOwner())
                    return;
                if (e.getKeyCode() != 10)
                    return;
                if (getText().equals(maxText.toString()))
                    return;
                value = Integer.parseInt(getText());
                setCorrectRange();
                handlePainting();
            }

            @Override
            public void keyReleased(KeyEvent e) {

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
            value = minValue;
            isAtMaxPoint = !changeOnMax;
        } else if (value >= maxValue) {
            value = maxValue;
            isAtMaxPoint = changeOnMax;
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
            SwingUtilities.invokeLater(performAction);
        } else {
            setText(String.valueOf(value));
            SwingUtilities.invokeLater(performAction);
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
