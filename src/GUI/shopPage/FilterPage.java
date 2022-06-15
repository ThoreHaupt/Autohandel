package GUI.shopPage;

import java.awt.Font;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.UIController;
import lib.Event.ChangeToCartListener;
import lib.technicalComponents.MyDocumentNumberFilter;
import lib.uiComponents.DisplayListeningField;
import lib.uiComponents.MLCheckBox;
import lib.uiComponents.MLLabel;
import lib.uiComponents.MLTextField;
import lib.uiComponents.RangeSlider;
import lib.uiComponents.RangeSliderPacket;
import lib.uiComponents.PrewrittenEditableTextField;

public class FilterPage extends JPanel {
    UIController uiController;

    public FilterPage(UIController uiController) {
        this.uiController = uiController;
        this.add(buildFilterPage());
    }

    private JPanel buildFilterPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(buildNorthFilterPage(), BorderLayout.NORTH);
        panel.add(buildCenterFilterPage(), BorderLayout.CENTER);
        //RangeSlider s = new RangeSlider();
        //panel.add(s);
        return panel;
    }

    private JPanel buildNorthFilterPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        MLLabel title = new MLLabel(uiController, "Filter");
        title.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 30));
        panel.add(title, BorderLayout.WEST);

        return panel;
    }

    private JPanel buildCenterFilterPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = .9;

        // maximum Spending
        c.gridx = 0;
        c.gridy = 0;
        MLLabel maximumSpendingDescribtion = new MLLabel(uiController, "Maximum Budget:");
        panel.add(maximumSpendingDescribtion, c);

        c.gridx = 1;
        PrewrittenEditableTextField mltfMaxSpending = new PrewrittenEditableTextField(uiController, "Maximum Budget",
                new MyDocumentNumberFilter());
        mltfMaxSpending.setColumns(15);
        //mltfMaxSpending.addActionListener(e -> );
        panel.add(mltfMaxSpending, c);

        // use maximum Spending to filter out Entries
        MLCheckBox checkBox = new MLCheckBox(uiController, "Filter non-affortable Offers");
        checkBox.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 10));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.9;
        c.gridwidth = 2;
        panel.add(checkBox, c);

        // current spending in shopping Cart --> not here, but rather in TopBar so it has better visibility
        JLabel currentSpending = new JLabel();
        currentSpending.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 8));
        double cashLeft = uiController.getController().getCurrentFreeBudget();
        uiController.getController().getCurrentFreeBudget();

        // Spending Range
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.9;
        c.gridwidth = 2;
        panel.add(buildSpendingRangeSelector(), c);
        // Electric
        // Brands

        return panel;
    }

    private JPanel buildSpendingRangeSelector() {
        JPanel backpanel = new JPanel();
        RangeSliderPacket rangeSlider = new RangeSliderPacket(uiController);

        backpanel.add(rangeSlider);
        return backpanel;
    }
}
