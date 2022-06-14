package lib.uiComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.text.DocumentFilter;

import Controller.Controller;
import GUI.UIController;
import lib.technicalComponents.MyDocumentNumberFilter;

public class PrewrittenTextField extends JPanel {

    UIController uiController;
    MLTextField textField;
    DocumentFilter filter;
    DocumentFilter defaultFilter;

    String defaultString;
    String currentString = "";

    boolean autoMaticChange = false;

    /**
     * @param c
     */
    public PrewrittenTextField(UIController c, String defaultString) {
        this.uiController = c;
        this.defaultString = defaultString;
        this.add(createTextField());
    }

    private JPanel createTextField() {
        JPanel panel = new JPanel();
        filter = new MyDocumentNumberFilter();
        defaultFilter = new DocumentFilter();

        panel.setMinimumSize(new DimensionUIResource(500, 500));
        textField = new MLTextField(uiController, defaultString);

        textField.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 11));
        textField.setEditable(true);
        textField.setDocumentFilter(filter);
        textField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (textField.getText().equals(uiController.getTransatedString(defaultString))) {
                    textField.setText("");
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
}
