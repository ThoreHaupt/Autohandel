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
import LocalizationLogic.Language;
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
    private Color errorColor = new Color(255, 51, 51);

    private Controller controller;
    private Color defaultUIcolor = new Color(255, 255, 255);

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
        Language currentLanguage = controller.getCurrentLanguage();
        lightmode = !lightmode;
        setTheme();
        pages = new THashMap<>();
        initializePages();

        topMenuBar = new TopMenuBar(this, new Dimension(window.getWidth(), topMenuBarHeight));
        window.getContentPane().removeAll();
        window.initMainPane();
        initializeTopMenuBar();

        window.repaint();
        window.revalidate();

        setWindowContent(currentPage);

        controller.setLanguage(currentLanguage);

    }

    public void setWindowContent(String mode) {
        System.out.println("setting to: " + mode);
        currentPage = mode;
        setMainWindowContent(pages.get(mode));
    }

    public Color getDefaultUIColor() {
        return defaultUIcolor;
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

    public Color getAffortableColor() {
        return new Color(30, 245, 38);
    }

    public Color getNotAffortableColor() {
        return new Color(245, 45, 30);
    }

    public Color getCloseToSpendingLimitColor() {
        return new Color(255, 210, 77);
    }

    /**
     * checks weather or not you are close to your spending limit and returns the color accordingly
     * Used by the total Price display in the Cart Tab for example
     * @return
     */
    public Color getColorBasedOnBudgetRestraint() {
        return getPriceBasedOnBudgetColor(0);
    }

    /**
     * returns weather or not a Product can be afforted. If it can not be afforted, this will return red
     * if it is close this will display Orange
     * if it will but you below 90% of your spending it will be green
     * 
     * this Propably does not make that much business sense, but it is a cool feature so whatever
     * 
     * @param price price on which the result will be calculated
     * @return the color to be used
     */
    public Color getPriceBasedOnBudgetColor(double price) {
        double budget = controller.getCurrentBudget();
        double usedBudget = controller.getCurrentCartValue() + price;

        if (budget == -1) {
            return getDefaultUIColor();
        }
        if (budget < usedBudget) {
            return getNotAffortableColor();
        }
        if (budget * 0.9 < usedBudget) {
            return getCloseToSpendingLimitColor();
        }
        return getAffortableColor();
    }

    public void displayDeniedLoginMessage(String string) {
        if (currentPage.equals(LOGIN_PAGE)) {
            ((UserLoginPage) pages.get(LOGIN_PAGE)).setErrorMessage(string);
        }
    }

    public void setDarkTheme(boolean setDarkTheme) {
        if (lightmode == setDarkTheme) {
            switchTheme();
        }
    }

    public Color getDefaultErrorColor() {
        return errorColor;
    }

    public void closeWindow() {
        window.dispose();
    }

    public int getUsableHeight() {
        return window.getHeight() - topMenuBarHeight;
    }
}
