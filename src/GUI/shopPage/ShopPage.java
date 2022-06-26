package GUI.shopPage;

import javax.swing.*;

import GUI.UIController;
import GUI.MainWindow;
import Model.Model;
import Model.ModelComponentes.Product;
import Model.UserComponentes.Filter;
import lib.uiComponents.PageSideHideMenu;

import java.awt.*;

public class ShopPage extends JPanel {

    UIController uiController;
    Filter filter;
    Product[] products;
    int currentMaxIndex = 0;

    PageSideHideMenu sideMenuManager;
    int sideMenuSize = 400;
    JPanel mainPanel;

    ShopGalleryEntry[] entries;

    /**
     * 
     */
    public ShopPage(UIController uiController) {
        this.uiController = uiController;
        this.filter = uiController.getController().getUser().getFilter();
        Model model = uiController.getController().getModel();
        createShopPage();
        model.addFilterChangeListener(e -> setEntriesWithCurrentFilter());
    }

    public void createShopPage() {
        this.sideMenuManager = new PageSideHideMenu(createMainPage(), createSideMenu(), sideMenuSize);
        add(sideMenuManager);
    }

    public JPanel createMainPage() {
        mainPanel = new JPanel();
        return mainPanel;
    }

    public void setShopEntries(JPanel EntryPanel) {
        mainPanel.removeAll();
        mainPanel.add(EntryPanel);
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    /**
     * default case when startup for example
     * Also When Filters are applied inside the UI and not being loaded externally through 
     * {@code setShopEntries(loadEntriesFromModel(someLoadedFilters))}
     */
    public void setEntriesWithCurrentFilter() {
        this.filter = uiController.getController().getUser().getFilter();
        setShopEntries(loadEntriesFromModel(filter));
    }

    public JPanel createSideMenu() {
        return new FilterPage(uiController);
    }

    public JPanel loadEntriesFromModel(Filter filter) {
        JPanel panel = new JPanel();

        products = uiController.getController().getFilteredProducts(filter);
        entries = new ShopGalleryEntry[products.length];
        currentMaxIndex = 0;

        panel.setBorder(BorderFactory.createEmptyBorder());

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.9;

        if (entries.length == 0) {
            JLabel l = new JLabel(uiController.getController().lc.s(
                    "No Entry found. Either there aren't any entries in Database, or your filters and search parameters did not mathc to any suitable entry."));
            panel.add(l);
            panel.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 20));
        } else {

        }

        for (; currentMaxIndex < Math.min(entries.length, 20); currentMaxIndex++) {
            entries[currentMaxIndex] = new ShopGalleryEntry(uiController, products[currentMaxIndex]);
            panel.add(entries[currentMaxIndex], c);
            c.gridy++;
            // System.out.println(entries[i].getMaximumSize());
        }

        // the scrollpane only works, if it has a set Size. Thus here is some logic,
        // that gets the perfekt size ig
        MainWindow window = uiController.getWindow();

        JScrollPane scrollPane = new JScrollPane(panel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(calculateOptimalShopMainPageSize());
        //making scrolling faster:
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        scrollPane.addMouseWheelListener(e -> {
            addMoreEntries(scrollPane, panel, c);
        });

        sideMenuManager.addSideHideExtentionStateChangeListener(e -> {
            scrollPane.setPreferredSize(calculateOptimalShopMainPageSize());
            scrollPane.revalidate();
        });
        window.addWindowSizeChangeListener(e -> {
            scrollPane.setPreferredSize(calculateOptimalShopMainPageSize());
            scrollPane.revalidate();
        });

        // I want to return a JPanel, not some JScrollBar, so I add it onto a new JPanel
        // and return that one
        JPanel returnJPanel = new JPanel();
        returnJPanel.setLayout(new BorderLayout());
        returnJPanel.add(scrollPane, BorderLayout.NORTH);
        return returnJPanel;
    }

    public Dimension calculateOptimalShopMainPageSize() {
        MainWindow window = uiController.getWindow();
        return new Dimension(
                (int) window.getWidth() - ((sideMenuManager.isExtended()) ? sideMenuSize : 0) - 60,
                window.getHeight() - uiController.getTopMenubarHeight() - 60);

    }

    public void setFilter(Filter filter) {
        if (this.filter.getUser().getUserFirstName().equals("Guest")) {

        }
    }

    public int getCurrentShopPageWidthWidth() {
        return (int) uiController.getWindow().getWidth() - ((sideMenuManager.isExtended()) ? sideMenuSize + 60 : 60);
    }

    public void addMoreEntries(JScrollPane scrollPane, JPanel panel, GridBagConstraints c) {
        System.out.println();
        if (currentMaxIndex == products.length - 1)
            return;
        double current = scrollPane.getVerticalScrollBar().getValue();
        int max = scrollPane.getVerticalScrollBar().getMaximum();
        while (current / max > 0.8) {
            if (currentMaxIndex == products.length - 1)
                break;
            entries[currentMaxIndex] = new ShopGalleryEntry(uiController, products[currentMaxIndex]);
            panel.add(entries[currentMaxIndex], c);
            currentMaxIndex++;
            c.gridy++;
            panel.revalidate();
            scrollPane.revalidate();
            current = scrollPane.getVerticalScrollBar().getValue();
            max = scrollPane.getVerticalScrollBar().getMaximum();
        }
        panel.revalidate();
        scrollPane.revalidate();
    }
}
