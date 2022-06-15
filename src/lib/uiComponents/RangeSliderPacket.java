package lib.uiComponents;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker.StateValue;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.text.DocumentFilter;

import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;

import GUI.UIController;
import lib.technicalComponents.DocumentNumberFilter;
import lib.technicalComponents.*;

public class RangeSliderPacket extends JPanel {

    UIController uiController;
    RangeSlider rangeSlider;

    int start = 5000;
    int end = 50000;
    int minorTickSpacing = 1000;
    int majorTickSpacing = 5000;
    int labelSpacing = 4; // amount of big values between labels

    int labelSize;
    MLLabel titleLabel;
    String titleLabelString = "Price Range:";
    int titleSize = 12;

    int outerLableSize;

    public RangeSliderPacket(UIController uiController) {
        this.uiController = uiController;
        this.setLayout(new BorderLayout());
        this.add(buildTitle(), BorderLayout.NORTH);
        this.add(buildRangeSlider(), BorderLayout.CENTER);
    }

    private JPanel buildRangeSlider() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        // left Label which displays the lower Value of the slider

        SmartMLTextField lowerLabelSliderValue = new SmartMLTextField(uiController, "no Limit", start, end, false);
        lowerLabelSliderValue.setBackground(uiController.getDefaultBackgroundcolor());
        lowerLabelSliderValue.setPreferredSize(new Dimension(50, 20));
        lowerLabelSliderValue.setBorder(BorderFactory.createEmptyBorder());

        // right Label which displays the upper Value of the slider

        SmartMLTextField upperLabelSliderValue = new SmartMLTextField(uiController, "no Limit", start, end, true);
        upperLabelSliderValue.setBackground(uiController.getDefaultBackgroundcolor());
        upperLabelSliderValue.setPreferredSize(new Dimension(50, 20));
        upperLabelSliderValue.setBorder(BorderFactory.createEmptyBorder());

        //Slider: 
        c.weightx = 0.6;
        //c.fill(GridBagConstraints.HORIZONTAL);

        c.gridx = 1;

        rangeSlider = new RangeSlider();

        rangeSlider.setMinimum(start);
        rangeSlider.setMaximum(end);

        rangeSlider.setValue(10000);
        rangeSlider.setUpperValue(40000);

        // create Labels
        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable = populateLabelTabel(labelTable);
        rangeSlider.setLabelTable(labelTable);

        rangeSlider.setMinorTickSpacing(minorTickSpacing);
        rangeSlider.setMajorTickSpacing(majorTickSpacing);

        rangeSlider.setPaintTicks(true);
        rangeSlider.setPaintLabels(true);

        rangeSlider.addChangeListener(e -> {
            lowerLabelSliderValue.setValue(rangeSlider.getValue());
            upperLabelSliderValue.setValue(rangeSlider.getUpperValue());
        });

        // addButtons and ActionListeners
        c.weightx = 0.2;

        //LeftLabel
        c.gridx = 0;
        c.gridy = 0;

        lowerLabelSliderValue.addActionListener(e -> {
            rangeSlider.setValue(lowerLabelSliderValue.getValue());
            rangeSlider.revalidate();
        });
        panel.add(lowerLabelSliderValue, c);

        // right Label
        c.gridx = 2;
        upperLabelSliderValue.addActionListener(e -> {
            rangeSlider.setUpperValue(upperLabelSliderValue.getValue());
            rangeSlider.revalidate();
        });
        panel.add(upperLabelSliderValue, c);

        // init label Values;
        lowerLabelSliderValue.setText(String.valueOf(rangeSlider.getValue()));
        upperLabelSliderValue.setText(String.valueOf(rangeSlider.getUpperValue()));

        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 0;

        panel.add(rangeSlider, c);

        return panel;

    }

    private Hashtable<Integer, JLabel> populateLabelTabel(Hashtable<Integer, JLabel> labelTable) {
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
        return labelTable;
    }

    private class blackField extends PrewrittenEditableTextField {

        public blackField(UIController uiController, double num) {
            super(uiController, "" + start, new DocumentNumberFilter());
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

    private JPanel buildTitle() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        titleLabel = new MLLabel(uiController, titleLabelString);
        titleLabel.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, titleSize));
        panel.add(titleLabel, BorderLayout.WEST);
        return panel;
    }
}
