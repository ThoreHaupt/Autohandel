package lib.uiComponents;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker.StateValue;
import javax.swing.text.DocumentFilter;

import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;

import GUI.UIController;
import lib.technicalComponents.MyDocumentNumberFilter;
import lib.technicalComponents.*;

public class RangeSliderPacket extends JPanel {

    UIController uiController;
    RangeSlider rangeSlider;

    int start = 5000;
    int end = 50000;
    int minorTickSpacing = 1000;
    int majorTickSpacing = 5000;
    int labelSpacing = 2; // amount of big values between labels

    int labelSize;

    int outerLableSize;

    public RangeSliderPacket(UIController uiController) {
        this.uiController = uiController;
        this.setLayout(new BorderLayout());
        this.add(buildRangeSlider());
    }

    private JPanel buildRangeSlider() {
        JPanel panel = new JPanel();
        rangeSlider = new RangeSlider();

        rangeSlider.setValue((end - start) / 4);
        rangeSlider.setUpperValue(3 * (end - start) / 4);
        rangeSlider.setMinimum(start);
        rangeSlider.setMaximum(end);

        // create Labels
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        populateLabelTabel(labelTable);

        rangeSlider.setLabelTable(labelTable);
        rangeSlider.setMinorTickSpacing(minorTickSpacing);
        rangeSlider.setMajorTickSpacing(majorTickSpacing);

        rangeSlider.setPaintTicks(true);
        rangeSlider.setPaintLabels(true);

        panel.add(rangeSlider);
        return panel;
    }

    private void populateLabelTabel(Hashtable<Integer, JLabel> labelTable) {
        System.out.println("jala");
        int labelDistance = (labelSpacing * majorTickSpacing);
        int reguilarLabelAmount = (end - start) / labelDistance + 1;

        JLabel l1 = new JLabel();
        blackField bf1 = new blackField(uiController, end);
        bf1.addActionListener(e -> {
            rangeSlider.setValue(bf1.getValue());
            revalidate();
        });
        l1.add(bf1);

        labelTable.put(Integer.valueOf(0), l1);

        Font labelFont = uiController.getDefaultFont().deriveFont(Font.PLAIN, 6);
        for (int i = 1; i < reguilarLabelAmount - 2; i++) {
            JLabel l = new JLabel("" + i * labelDistance);
            l.setFont(labelFont);
            labelTable.put(Integer.valueOf(i), l);
        }

        JLabel l2 = new JLabel();
        blackField bf2 = new blackField(uiController, end);
        bf2.addActionListener(e -> {
            rangeSlider.setValue(bf2.getValue());
            revalidate();
        });
        l2.add(bf2);

        labelTable.put(Integer.valueOf(reguilarLabelAmount), l1);

    }

    private class blackField extends PrewrittenEditableTextField {

        public blackField(UIController uiController, double num) {
            super(uiController, "" + start, new MyDocumentNumberFilter());
            textField.setEditable(true);
            this.textField.setAutoLanguageAdaption(false);
            textField.setBorder(BorderFactory.createEmptyBorder());
            this.setBorder(new RoundedBorder(5));
            this.setBackground(Color.BLACK.brighter());

        }

        public void addActionListener(ActionListener l) {
            this.textField.addActionListener(l);
        }

        public int getValue() {
            return Integer.parseInt(textField.getText());
        }

    }
}
