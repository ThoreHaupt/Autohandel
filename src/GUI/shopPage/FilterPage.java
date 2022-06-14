package GUI.shopPage;

import javax.swing.JPanel;

import GUI.UIController;
import lib.uiComponents.RangeSelectorSlider;

public class FilterPage extends JPanel {
    UIController uiController;

    public FilterPage(UIController uiController) {
        this.uiController = uiController;
        this.add(buildFilterPage());
    }

    private JPanel buildFilterPage() {
        JPanel panel = new JPanel();
        RangeSelectorSlider s = new RangeSelectorSlider();
        panel.add(s);
        return panel;
    }
}
