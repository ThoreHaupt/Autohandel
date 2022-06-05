package GUI.topMenuBar;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentListener;

public class CapableComboBoxEditor implements ComboBoxEditor {

    JTextField item;

    public CapableComboBoxEditor(String t) {
        item = new JTextField(t, 30);
    }

    @Override
    public Component getEditorComponent() {
        return item;
    }

    @Override
    public void setItem(Object anObject) {
        if (anObject != null) {
            item.setText(anObject.toString());
        } else {
            anObject = null;
        }

    }

    @Override
    public String getItem() {
        return item.getText();
    }

    @Override
    public void selectAll() {
        throw new UnsupportedOperationException(
                "Not supported yet. in select All");
    }

    @Override
    public void addActionListener(ActionListener l) {
        item.addActionListener(l);
    }

    @Override
    public void removeActionListener(ActionListener l) {
        item.removeActionListener(l);
    }

    public void addDocumentListener(DocumentListener l) {
        item.getDocument().addDocumentListener(l);
    }

    public void setText(String text) {
        item.setText(text);
    }

    public String getText() {
        return item.getText();
    }

}