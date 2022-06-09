package lib.uiComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.DimensionUIResource;

import Controller.Controller;

public class prewrittenTextField {

    Controller c;

    /**
     * @param c
     */
    public prewrittenTextField(Controller c) {
        this.c = c;
        createSearchBar();
    }

    private JPanel createSearchBar() {
        JPanel panel = new JPanel();
        Font font = new Font("SANS_SERIF", Font.PLAIN, 18);
        String defaultText = c.lc.s("search database");
        panel.setMinimumSize(new DimensionUIResource(500, 500));
        JTextField textField = new JTextField(30);
        // textField.setFont(font);
        // textField.setSize(new DimensionUIResource(500, 90));
        textField.setText(defaultText);
        textField.setEditable(true);

        textField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change.org");

            }

        });

        textField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (textField.getText().equals(defaultText))
                    textField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().equals(""))
                    textField.setText(defaultText);
            }

        });
        return panel;
    }
}
