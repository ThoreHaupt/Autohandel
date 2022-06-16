package lib.uiComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.text.DocumentFilter;

import GUI.UIController;
import lib.uiComponents.technicalUIComponents.DocumentNumberFilter;

public class PrewrittenEditableTextField extends JPanel {

    UIController uiController;
    MLTextField textField;
    DocumentFilter filter;
    DocumentFilter defaultFilter;

    String defaultString;
    String currentString = "";

    String fokusEditText = "";

    boolean autoMaticChange = false;

    /**
     * @param c
     */
    public PrewrittenEditableTextField(UIController c, String defaultString, DocumentFilter filter) {
        this.uiController = c;
        this.defaultString = defaultString;
        this.defaultFilter = new DocumentFilter();
        this.filter = filter;
        this.add(createTextField());
    }

    /**
     * @param c
     */
    public PrewrittenEditableTextField(UIController c, String defaultString) {
        this.uiController = c;
        this.defaultString = defaultString;
        this.defaultFilter = new DocumentFilter();
        this.filter = defaultFilter;
        this.add(createTextField());
    }

    private JPanel createTextField() {
        JPanel panel = new JPanel();

        panel.setMinimumSize(new DimensionUIResource(500, 500));
        textField = new MLTextField(uiController, defaultString);

        textField.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 11));
        textField.setEditable(true);
        textField.setDocumentFilter(filter);

        textField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (textField.getText().equals(uiController.getTransatedString(defaultString))) {
                    textField.setText(fokusEditText);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.setDocumentFilter(defaultFilter);
                if (textField.getText().equals(""))
                    textField.setText(defaultString);
                textField.setDocumentFilter(filter);
            }

        });

        panel.add(textField);
        return panel;
    }

    public void setColumns(int i) {
        textField.setColumns(i);
    }

    protected void setTextDefault() {
        textField.setDocumentFilter(defaultFilter);
        textField.setText(defaultString);
        textField.setDocumentFilter(filter);
    }

    protected void setFokusEditText(String s) {
        this.fokusEditText = s;
    }

    public void changeFilter(DocumentFilter filter) {
        this.filter = filter;
        this.textField.setDocumentFilter(filter);
    }

    public void setForeGround(Color c) {
        textField.setForeground(c);
    }

    public String getText() {
        if (textField.getText().equals(uiController.getTransatedString(defaultString))) {
            return "";
        } else {
            return textField.getText();
        }
    }

    @Override
    public void setPreferredSize(Dimension d) {
        super.setPreferredSize(d);
        textField.setPreferredSize(d);
    }
}
