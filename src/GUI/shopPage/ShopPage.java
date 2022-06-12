package GUI.shopPage;

import javax.swing.*;
import javax.swing.SpringLayout.Constraints;

import GUI.UIController;
import Model.ModelComponentes.Car;
import Model.UserComponentes.Filter;
import lib.uiComponents.PageSideHideMenu;

import java.awt.*;
import java.awt.event.*;

public class ShopPage extends JPanel {

    UIController uiController;
    Filter filter;

    PageSideHideMenu sideMenuManager;
    int sideMenuSize = 500;
    JPanel mainPanel;

    /**
     * 
     */
    public ShopPage(UIController uiController) {
        this.uiController = uiController;
        this.filter = uiController.getController().getUser().getFilter();
        createShopPage();
    }

    public void createShopPage() {
        this.sideMenuManager = new PageSideHideMenu(createMainPage(), createSideMenu(), sideMenuSize);
        add(sideMenuManager);
    }

    public JPanel createMainPage() {
        mainPanel = new JPanel();
        mainPanel.add(loadEntriesFromModel(filter));
        return mainPanel;
    }

    public JPanel createSideMenu() {
        return new JPanel();
    }

    public JPanel loadEntriesFromModel(Filter filter) {
        JPanel panel = new JPanel();

        Car[] entries = uiController.getController().getOptions(filter);
        panel.setBorder(BorderFactory.createEmptyBorder());

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;

        if (entries.length == 0) {
            JLabel l = new JLabel(uiController.getController().lc.s(
                    "No Entry found. Either there aren't any entries in Database,\n or your filters and search parameters did not mathc to any suitable entry."));
            panel.add(l);
            panel.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 20));
        }

        for (int i = 0; i < entries.length; i++) {
            panel.add(new ShopGalleryEntry(uiController, entries[i]), c);
        }

        panel.add(Box.createVerticalGlue());

        // the scrollpane only works, if it has a set Size. Thus here is some logic,
        // that gets the perfekt size ig
        Window window = uiController.getWindow();

        JScrollPane scrollPane = new JScrollPane(panel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(
                new Dimension(
                        (int) window.getWidth() - 60,
                        window.getHeight() - uiController.getTopMenubarHeight()));

        window.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                scrollPane.setPreferredSize(
                        new Dimension(
                                (int) window.getWidth() - ((sideMenuManager.isExtended()) ? sideMenuSize + 60 : 60),
                                window.getHeight() - uiController.getTopMenubarHeight()));

                System.out.println("changed Window Size");
                scrollPane.revalidate();
            }
        });

        // I want to return a JPanel, not some JScrollBar, so I add it onto a new JPanel
        // and return that one
        JPanel returnJPanel = new JPanel();
        returnJPanel.setLayout(new BorderLayout());
        returnJPanel.add(scrollPane, BorderLayout.NORTH);
        return returnJPanel;
    }

    public void setFilter(Filter filter) {
        if (this.filter.getUser().getUserName().equals("Guest")) {

        }
    }

}
