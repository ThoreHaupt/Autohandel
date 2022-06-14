package GUI.shopPage;

import javax.swing.*;

import GUI.UIController;
import GUI.MainWindow;
import Model.ModelComponentes.Car;
import Model.UserComponentes.Filter;
import lib.uiComponents.PageSideHideMenu;

import java.awt.*;

public class ShopPage extends JPanel {

    UIController uiController;
    Filter filter;

    PageSideHideMenu sideMenuManager;
    int sideMenuSize = 300;
    JPanel mainPanel;

    ShopGalleryEntry[] entries;

    /**
     * 
     */
    public ShopPage(UIController uiController) {
        this.uiController = uiController;
        this.filter = uiController.getController().getUser().getFilter();
        createShopPage();

        /* uiController.getWindow().addWindowSizeChangeListener(new WindowSizeChangeListener() {
        
            @Override
            public void windowSizeChanged(WindowSizeChangeEvent event) {
                /* int optimalWidth = getWidth() - 60;
                if (entries != null) {
                    for (ShopGalleryEntry entry : entries) {
                        entry.revalidateBufferSize(optimalWidth);
                    }
                }
                System.out.println("hello");
                revalidate();
                repaint(); */

        /* removeAll();
        setEntriesWithCurrentFilter();
        //repaint();
        revalidate(); 
        
                */
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
        mainPanel.add(EntryPanel);
        repaint();
        revalidate();
    }

    /**
     * default case when startup for example
     * Also When Filters are applied inside the UI and not being loaded externally through 
     * {@code setShopEntries(loadEntriesFromModel(someLoadedFilters))}
     */
    public void setEntriesWithCurrentFilter() {
        setShopEntries(loadEntriesFromModel(filter));
    }

    public JPanel createSideMenu() {
        return new FilterPage(uiController);
    }

    public JPanel loadEntriesFromModel(Filter filter) {
        JPanel panel = new JPanel();

        Car[] cars = uiController.getController().getOptions(filter);
        entries = new ShopGalleryEntry[cars.length];
        panel.setBorder(BorderFactory.createEmptyBorder());

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.weightx = 1.0;

        if (entries.length == 0) {
            JLabel l = new JLabel(uiController.getController().lc.s(
                    "No Entry found. Either there aren't any entries in Database,\n or your filters and search parameters did not mathc to any suitable entry."));
            panel.add(l);
            panel.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 20));
        }

        for (int i = 0; i < entries.length; i++) {
            entries[i] = new ShopGalleryEntry(uiController, cars[i]);
            panel.add(entries[i], c);
            // System.out.println(entries[i].getMaximumSize());
        }

        panel.add(Box.createVerticalGlue());

        // the scrollpane only works, if it has a set Size. Thus here is some logic,
        // that gets the perfekt size ig
        MainWindow window = uiController.getWindow();

        JScrollPane scrollPane = new JScrollPane(panel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(
                new Dimension(
                        (int) window.getWidth() - 60,
                        window.getHeight() - uiController.getTopMenubarHeight()));

        /* // When the fullWindow Button gets clicked/ minimize usw
        window.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                scrollPane.setPreferredSize(calculateOptimalShopMainPageSize());
                // System.out.println("changed Window Size");
                scrollPane.revalidate();
            }
        });
        
        // when someone changes the window size manually
        window.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                scrollPane.setPreferredSize(calculateOptimalShopMainPageSize());
                // System.out.println("changed Window Size");
                scrollPane.revalidate();
            }
        
        }); */

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
                window.getHeight() - uiController.getTopMenubarHeight());

    }

    public void setFilter(Filter filter) {
        if (this.filter.getUser().getUserName().equals("Guest")) {

        }
    }

    public int getCurrentShopPageWidthWidth() {
        return (int) uiController.getWindow().getWidth() - ((sideMenuManager.isExtended()) ? sideMenuSize + 60 : 60);
    }

}
