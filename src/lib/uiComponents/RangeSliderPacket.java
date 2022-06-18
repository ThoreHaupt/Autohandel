package lib.uiComponents;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;

import GUI.UIController;
import lib.technicalComponents.*;
import lib.uiComponents.technicalUIComponents.DocumentNumberFilter;
import lib.uiComponents.technicalUIComponents.SpendingrangeIntervall;
import lib.uiComponents.technicalUIComponents.RoundedBorder;

public class RangeSliderPacket extends JPanel {

    UIController uiController;
    RangeSlider rangeSlider;

    SmartMLTextField lowerLabelSliderValue;
    SmartMLTextField upperLabelSliderValue;

    int minimalSetting = 5000;
    int maximalSetting = 50000;
    int minorTickSpacing = 1000;
    int majorTickSpacing = 5000;
    int labelSpacing = 4; // amount of big values between labels

    int labelSize;
    MLLabel titleLabel;
    String titleLabelString = "Price Range:";
    int titleSize = 13;

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

        rigitFreeSpace space = new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(10, 5));

        c.gridwidth = 3;
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 0;

        panel.add(space, c);

        // left Label which displays the lower Value of the slider

        lowerLabelSliderValue = new SmartMLTextField(uiController, "no Limit", minimalSetting, maximalSetting, false);
        lowerLabelSliderValue.setBackground(uiController.getDefaultBackgroundcolor());
        lowerLabelSliderValue.setPreferredSize(new Dimension(70, 20));
        lowerLabelSliderValue.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 12));
        lowerLabelSliderValue.setBorder(BorderFactory.createEmptyBorder());

        // right Label which displays the upper Value of the slider

        upperLabelSliderValue = new SmartMLTextField(uiController, "no Limit", minimalSetting, maximalSetting, true);
        upperLabelSliderValue.setBackground(uiController.getDefaultBackgroundcolor());
        upperLabelSliderValue.setPreferredSize(new Dimension(70, 20));
        upperLabelSliderValue.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 12));
        upperLabelSliderValue.setBorder(BorderFactory.createEmptyBorder());

        //Slider: 

        rangeSlider = new RangeSlider();

        rangeSlider.setMinimum(minimalSetting);
        rangeSlider.setMaximum(maximalSetting);

        rangeSlider.setValue(10000);
        rangeSlider.setUpperValue(40000);

        // create Labels not working rn, so I commentet it out. 
        // could later be used to change the maximum FilterRange to a custom value
        /* Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable = populateLabelTabel(labelTable);
        rangeSlider.setLabelTable(labelTable); 
        rangeSlider.setPaintLabels(true);*/

        rangeSlider.setMinorTickSpacing(minorTickSpacing);
        rangeSlider.setMajorTickSpacing(majorTickSpacing);

        rangeSlider.setPaintTicks(true);

        // add Listner for RangeSlider

        rangeSlider.addChangeListener(e -> {
            lowerLabelSliderValue.setValue(rangeSlider.getValue());
            upperLabelSliderValue.setValue(rangeSlider.getUpperValue());
        });

        // addButtons and their ActionListeners

        lowerLabelSliderValue.addActionListener(e -> {
            rangeSlider.setValue(lowerLabelSliderValue.getValue());
            rangeSlider.revalidate();
        });

        //LeftLabel
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        panel.add(lowerLabelSliderValue, c);

        // right Label
        c.gridx = 2;
        upperLabelSliderValue.addActionListener(e -> {
            rangeSlider.setUpperValue(upperLabelSliderValue.getValue());
            rangeSlider.revalidate();
        });
        panel.add(upperLabelSliderValue, c);

        // init label Values;
        lowerLabelSliderValue.setValue(rangeSlider.getValue());
        upperLabelSliderValue.setValue(rangeSlider.getUpperValue());

        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 1;

        panel.add(rangeSlider, c);

        return panel;

    }

    private Hashtable<Integer, JLabel> populateLabelTabel(Hashtable<Integer, JLabel> labelTable) {
        int labelDistance = (labelSpacing * majorTickSpacing);
        int reguilarLabelAmount = (maximalSetting - minimalSetting) / labelDistance + 1;

        JLabel l1 = new JLabel();
        blackField bf1 = new blackField(uiController, maximalSetting);
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
        blackField bf2 = new blackField(uiController, maximalSetting);
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
            super(uiController, "" + minimalSetting, new DocumentNumberFilter());
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

    public void addChangeListener(ChangeListener l) {
        rangeSlider.addChangeListener(l);
    }

    public SpendingrangeIntervall getIntervall() {
        return new SpendingrangeIntervall(rangeSlider.getValue(), rangeSlider.getUpperValue());
    }

    public void setIntervall(SpendingrangeIntervall spendingIntervall) {
        rangeSlider.setValue(spendingIntervall.getLower());
        rangeSlider.setUpperValue((spendingIntervall.getUpper() == maximalSetting) ? -1 : spendingIntervall.getUpper());
    }

}
