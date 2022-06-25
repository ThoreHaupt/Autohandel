package lib.uiComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.DocumentFilter;

import Controller.Controller;
import GUI.UIController;
import lib.uiComponents.technicalUIComponents.CustomTextComponent;

public class PrewrittenEditableTextField extends JPanel implements CustomTextComponent {

    UIController uiController;
    Controller controller;
    MLTextField textField;
    DocumentFilter filter;
    DocumentFilter defaultFilter;

    String defaultString;
    String currentDefaultString = "";

    String fokusEditText = "";

    boolean automaticChange = false;

    /**
     * @param c
     */
    public PrewrittenEditableTextField(UIController c, String defaultString, DocumentFilter filter) {
        this.uiController = c;
        controller = uiController.getController();
        this.defaultString = defaultString;
        this.defaultFilter = new DocumentFilter();
        this.currentDefaultString = uiController.getTranslatedString(defaultString);
        this.filter = filter;
        setLayout(new BorderLayout());
        this.add(createTextField(), BorderLayout.CENTER);
        textField.setAutoLanguageAdaption(false);
        this.setTextDefault();
        uiController.getController().lc.addLanguageChangeListener(e -> updateLanguage());
    }

    /**
     * @param c
     */
    public PrewrittenEditableTextField(UIController c, String defaultString) {
        this(c, defaultString, new DocumentFilter());
    }

    private JPanel createTextField() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        //panel.setMinimumSize(new DimensionUIResource(500, 500));
        textField = new MLTextField(uiController, defaultString);

        panel.setBackground(new Color(0, 255, 0));
        //textField.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 11));
        textField.setEditable(true);
        textField.setDocumentFilter(filter);

        textField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (textField.getText().equals(currentDefaultString)) {
                    textField.setText(fokusEditText);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().equals("")) {
                    textField.setDocumentFilter(defaultFilter);
                    textField.setText(defaultString);
                    textField.setDocumentFilter(filter);
                }
            }

        });
        textField.setChangeAsAction();
        panel.add(textField, BorderLayout.CENTER);
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

    private void updateLanguage() {
        if (getText().equals("")) {
            System.out.println("updateing language");
            currentDefaultString = uiController.getTranslatedString(defaultString);
            setTextDefault();
        }
    }

    public void changeFilter(DocumentFilter filter) {
        this.filter = filter;
        this.textField.setDocumentFilter(filter);
    }

    public void setForeGround(Color c) {
        textField.setForeground(c);
    }

    public String getText() {
        if (textField.getText().equals(currentDefaultString)) {
            System.out.println("sucewss ig");
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

    @Override
    public void resetValue() {

    }

    public void setText(String text) {
        textField.setText(text);
        if (textField.getText().equals("")) {
            textField.setDocumentFilter(defaultFilter);
            textField.setText(defaultString);
            textField.setDocumentFilter(filter);
        }
    }

    public void addActionListener(ActionListener l) {
        textField.addActionListener(l);
    }

    public void addFocusListener(FocusListener l) {
        textField.addFocusListener(l);
    }

}
