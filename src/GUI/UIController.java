package GUI;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.plaf.synth.SynthScrollBarUI;

import java.awt.*;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import Controller.Controller;
import GUI.cartPage.CartPage;
import GUI.configurator.Configurator;
import GUI.shopPage.ShopPage;
import GUI.topMenuBar.TopMenuBar;
import GUI.userPage.UserLoginPage;
import GUI.userPage.UserPage;
import GUI.userPage.UserSignUpPage;
import lib.DataStructures.HashMapImplementation.THashMap;

public class UIController {

    MainWindow window;
    int windowWidth;
    int windowheight;

    public static final String MAINSTORE_PAGE = "store";
    public static final String CART_PAGE = "cart";
    public static final String CONFIGURATOR_PAGE = "payment";
    public static final String LOGIN_PAGE = "login";
    public static final String SIGNUP_PAGE = "signup";
    public static final String USERPROFILE_PAGE = "userProfile";

    JPanel topMenuBar;
    int topMenuBarHeight = 60;
    THashMap<String, JPanel> pages = new THashMap<>();
    String currentPage = MAINSTORE_PAGE;

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
        initializePages();

        topMenuBar = new TopMenuBar(this, new Dimension(window.getWidth(), topMenuBarHeight));
        initializeTopMenuBar();

        setMainWindowContent(pages.get(MAINSTORE_PAGE));
    }

    private void initializeTopMenuBar() {
        window.getContentPane().add(topMenuBar, BorderLayout.NORTH);
    }

    private void initializePages() {
        pages.put(MAINSTORE_PAGE, new ShopPage(this));
        pages.put(CART_PAGE, new CartPage(this));
        pages.put(CONFIGURATOR_PAGE, new Configurator(this));

        pages.put(USERPROFILE_PAGE, new UserPage(this));
        pages.put(SIGNUP_PAGE, new UserSignUpPage(this));
        pages.put(LOGIN_PAGE, new UserLoginPage(this));

        ((ShopPage) pages.get(MAINSTORE_PAGE)).setEntriesWithCurrentFilter();
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

    public void switchTheme() {
        System.out.println("changig Theme ? idk");
        lightmode = !lightmode;
        setTheme();
        /* topMenuBar.repaint();
        topMenuBar.revalidate();
        topMenuBar.updateUI();
        
        window.getMainPane().updateUI();
        window.getMainPane().revalidate();
        window.getMainPane().repaint();
        
        pages.forEach(e -> e.updateUI());
        pages.forEach(e -> e.revalidate());
        pages.forEach(e -> e.repaint()); */

        pages = new THashMap<>();
        initializePages();

        topMenuBar = new TopMenuBar(this, new Dimension(window.getWidth(), topMenuBarHeight));
        window.getContentPane().removeAll();
        window.initMainPane();
        initializeTopMenuBar();

        window.repaint();
        window.revalidate();

        setWindowContent(currentPage);

    }

    public void setWindowContent(String mode) {
        System.out.println("setting to: " + mode);
        currentPage = mode;
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

    private void setMainWindowContent(JPanel page) {
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

    public Color getAffortableColor() {
        return new Color(30, 245, 38);
    }

    public Color getNotAffortableColor() {
        return new Color(245, 45, 30);
    }

    public void displayDeniedLoginMessage(String string) {
    }
}
