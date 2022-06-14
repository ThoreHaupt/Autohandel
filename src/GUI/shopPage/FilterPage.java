package GUI.shopPage;

import java.awt.Font;
import java.awt.*;

import javax.swing.JPanel;

import GUI.UIController;
import lib.uiComponents.MLLabel;
import lib.uiComponents.MLTextField;
import lib.uiComponents.RangeSlider;
import lib.uiComponents.PrewrittenTextField;

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
        c.weightx = 1.0;

        // maximum Spending
        PrewrittenTextField mltfMaxSpending = new PrewrittenTextField(uiController, "maximum Budget");
        mltfMaxSpending.setColumns(20);
        panel.add(mltfMaxSpending, c);
        // Spending Range

        // Electric
        // Brands

        return panel;
    }

    private JPanel buildSpendingRangeSelector() {
        JPanel backpanel = new JPanel();
        RangeSlider rangeSlider = new RangeSlider();

        return backpanel;
    }
}
