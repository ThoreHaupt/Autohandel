package GUI;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.*;

import java.awt.*;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import Controller.Controller;
import GUI.cartPage.CartPage;
import GUI.configurator.Configurator;
import GUI.shopPage.ShopPage;
import GUI.topMenuBar.TopMenuBar;
import GUI.userPage.UserPage;

public class UIController {

    MainWindow window;
    int windowWidth;
    int windowheight;

    public static final String MAINSTORE_PAGE = "store";
    public static final String CART_PAGE = "cart";
    public static final String PAYMENT_PAGE = "payment";
    public static final String USERPROFILE_PAGE = "userProfile";

    JPanel topMenuBar;
    int topMenuBarHeight = 60;
    HashMap<String, JPanel> pages = new HashMap<>();

    boolean lightmode = false;
    private Font defaultFont = new Font("Segoe", Font.PLAIN, 20);
    private Color defaultAccentColor = new Color(75, 150, 255);
    private Color defaultBackgroundColor = null;

    private Controller controller;

    public UIController(Controller controller) {
        loadStartUpInfoFile();
        setTheme();

        this.controller = controller;

        window = new MainWindow(this);
        topMenuBar = new TopMenuBar(this, new Dimension(window.getWidth(), topMenuBarHeight));

        initializePages();
        initializeTopMenuBar();
        setMainWindowContent(pages.get(MAINSTORE_PAGE));
        ((ShopPage) pages.get(MAINSTORE_PAGE)).setEntriesWithCurrentFilter();
    }

    private void initializeTopMenuBar() {
        window.getContentPane().add(topMenuBar, BorderLayout.NORTH);
    }

    private void initializePages() {
        pages.put(MAINSTORE_PAGE, new ShopPage(this));
        pages.put(CART_PAGE, new CartPage(this));
        pages.put(PAYMENT_PAGE, new Configurator(this));
        pages.put(USERPROFILE_PAGE, new UserPage(this));

    }

    /**
     * reads startupproperties from controller, which got it from a file storing /
     * serializable object which is saved
     * 
     */
    private void loadStartUpInfoFile() {
    }

    private void setTheme() {
        if (lightmode) {
            FlatLightLaf.setup();
        } else {
            FlatDarkLaf.setup();
        }
    }

    public void setWindowContent(String mode) {
        System.out.println("setting to: " + mode);
        setMainWindowContent(pages.get(mode));
    }

    public Color getDefaultUIColor() {
        return null;
    }

    public Color getDefaultBackgroundcolor() {
        return defaultBackgroundColor;
    }

    public Controller getController() {
        return controller;
    }

    public Font getDefaultFont() {
        return defaultFont;
    }

    public Color getDefaultAccentColor() {
        return defaultAccentColor;
    }

    public void setMainWindowContent(JPanel page) {
        JPanel mainPanel = window.getMainPane();
        mainPanel.removeAll();
        mainPanel.add(page, BorderLayout.CENTER);

        mainPanel.repaint();
        mainPanel.revalidate();
    }

    public MainWindow getWindow() {
        return window;
    }

    public int getTopMenubarHeight() {
        return topMenuBarHeight;
    }

    public JPanel getStandartPanel(String page) {
        return pages.get(page);
    }

    public String getTransatedString(String string) {
        return controller.getLocalizationController().s(string);
    }
}
