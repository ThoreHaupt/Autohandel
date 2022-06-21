package GUI.shopPage;

import java.awt.Font;
import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.Controller;
import GUI.UIController;
import Model.UserComponentes.Filter;
import lib.Other.SupportingCalculations;
import lib.uiComponents.MLCheckBox;
import lib.uiComponents.MLLabel;
import lib.uiComponents.RangeSliderPacket;
import lib.uiComponents.technicalUIComponents.DocumentNumberFilter;
import lib.uiComponents.PrewrittenEditableTextField;

public class FilterPage extends JPanel {
    UIController uiController;
    Controller controller;
    Filter currentFilter;

    public FilterPage(UIController uiController) {
        this.uiController = uiController;
        this.controller = uiController.getController();
        currentFilter = controller.getCurrentUser().getFilter();
        this.add(buildFilterPage());
        controller.addNewUserLoginListener(e -> updateFilterPage());
    }

    private void updateFilterPage() {
        removeAll();
        currentFilter = controller.getCurrentUser().getFilter();
        this.add(buildFilterPage());
        revalidate();
        repaint();
    }

    private JPanel buildFilterPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(buildNorthFilterPage(), BorderLayout.NORTH);
        panel.add(buildCenterFilterPage(), BorderLayout.CENTER);
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
                new DocumentNumberFilter());
        mltfMaxSpending.setColumns(15);
        double currentMaxSpending = currentFilter.getMaximumBudget();
        if (currentMaxSpending > 0) {
            mltfMaxSpending.setText(SupportingCalculations.round(currentMaxSpending, 2) + "");
        }
        mltfMaxSpending.addActionListener(e -> {
            String text = mltfMaxSpending.getText();
            System.out.println("change to max Budget Document");
            if (text.equals("")) {
                currentFilter.setMaxBudget(-1);
            } else {
                int newMaxBudgetRequest = Integer.parseInt(text);
                if (currentFilter.getUser().getCart().getTotalPrice() <= newMaxBudgetRequest) {
                    currentFilter.setMaxBudget(newMaxBudgetRequest);
                } else {
                    int newMaxBudget = (int) currentFilter.getUser().getCart().getTotalPrice() + 1;
                    currentFilter.setMaxBudget(newMaxBudget);
                    mltfMaxSpending.setText(newMaxBudget + "");
                }
            }

        });
        panel.add(mltfMaxSpending, c);

        // use maximum Spending to filter out Entries
        MLCheckBox checkBox = new MLCheckBox(uiController, "Filter non-affortable Offers");
        checkBox.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 10));
        checkBox.setSelected(currentFilter.getFilterNonAffortable());
        checkBox.addItemListener(e -> currentFilter.setFilterMaxSpending(((MLCheckBox) e.getSource()).isSelected()));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.9;
        c.gridwidth = 2;
        panel.add(checkBox, c);

        // current spending in shopping Cart --> not here, but rather in TopBar so it has better visibility

        // Spending Range
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.9;
        c.gridwidth = 2;
        RangeSliderPacket rangeSlider = new RangeSliderPacket(uiController);
        rangeSlider.setIntervall(currentFilter.getSpendingIntervall());
        rangeSlider.addChangeListener(e -> currentFilter.setSpendingRange(rangeSlider.getIntervall()));
        panel.add(rangeSlider, c);
        // Engine Style

        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0.9;
        c.gridwidth = 2;

        MLLabel engineSectionTitle = new MLLabel(uiController, "Engine");

        panel.add(engineSectionTitle, c);

        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 0.9;
        c.gridwidth = 2;

        // Engine Filter checkboxes
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new GridLayout(1, 2));

        // electro Engine
        MLCheckBox electro = new MLCheckBox(uiController, "Electric");
        electro.setSelected(currentFilter.getElectro());
        electro.addItemListener(e -> currentFilter.setElectro(((MLCheckBox) e.getSource()).isSelected()));
        checkBoxPanel.add(electro, 0);

        // ice Engine
        MLCheckBox ICE = new MLCheckBox(uiController, "Internal Combustion");
        ICE.setSelected(currentFilter.getICE());
        ICE.addItemListener(e -> currentFilter.setICE(((MLCheckBox) e.getSource()).isSelected()));
        checkBoxPanel.add(ICE, 1);
        panel.add(checkBoxPanel, c);

        // Brands

        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 0.9;
        c.gridwidth = 2;

        JPanel brandFilter = new BrandsSelectorList(uiController, currentFilter);

        panel.add(brandFilter, c);

        return panel;
    }

}
