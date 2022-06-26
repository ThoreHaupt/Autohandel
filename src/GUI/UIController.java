package GUI;

import java.awt.Color;

import javax.swing.*;

import java.awt.*;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import Controller.Controller;
import Controller.StartupProperties;
import GUI.shopPage.ShopPage;
import GUI.topMenuBar.TopMenuBar;
import GUI.userPage.ThankYouPage;
import GUI.userPage.UserPage;
import GUI.userPage.UserLoginSignUP.UserLoginPage;
import GUI.userPage.UserLoginSignUP.UserSignUpPage;
import LocalizationLogic.Language;
import lib.DataStructures.HashMapImplementation.THashMap;

/**
 * The uiController manges the entire UI and is also responsible for providing 
 * standart default uiElements.
 * It also gets passed around almost all uiElements and has references to the Controller, which in turn has references to the model
 * This is not the cleanest MVC setup, but for this it is pretty practical, cuz it eleminates the need to write 1000 getters and setters in the
 * controller and UIcontroller. 
 * In the case that this programm would use a server client model, the usual MVC architecture could easily be modified, in this setup this is only
 * paritally possible. Mostly there are the needed starting solutions, but there would still be the need for some change
 */
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
    public static final String THANK_YOU_4_PUCHASE = "thanks";

    private Controller controller;

    JPanel topMenuBar;
    int topMenuBarHeight = 60;
    THashMap<String, JPanel> pages = new THashMap<>();
    String currentPage = MAINSTORE_PAGE;

    boolean lightmode = false;
    private Font defaultFont = new Font("Segoe", Font.PLAIN, 20);

    private Color defaultAccentColor = new Color(75, 150, 255);
    private Color defaultBackgroundColor = null;
    private Color errorColor = new Color(255, 51, 51);
    private Color defaultRedColor = new Color(171, 3, 3);
    private Color defaultFontColor = null;
    private Color defaultUIcolor = new Color(255, 255, 255);

    /**
     * The constructor for the uiController initializes alle the major elements of the UI.
     * firstly ist loads the start up information, which was planed to hold information on the preferred theme settings and so on
     * @param controller
     */
    public UIController(Controller controller) {
        this.controller = controller;

        loadStartUpInfoFile();
        setTheme();

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
        //pages.put(CART_PAGE, new CartPage(this));

        pages.put(USERPROFILE_PAGE, new UserPage(this));
        pages.put(SIGNUP_PAGE, new UserSignUpPage(this));
        pages.put(LOGIN_PAGE, new UserLoginPage(this));

        pages.put(THANK_YOU_4_PUCHASE, new ThankYouPage(this));

        // beause shop page needs references
        ((ShopPage) pages.get(MAINSTORE_PAGE)).setEntriesWithCurrentFilter();
    }

    /**
     * reads startupproperties from controller, which got it from a file storing /
     * serializable object which is saved
     * 
     */
    private void loadStartUpInfoFile() {
        StartupProperties startupP = controller.getStartUpInfoFile();
        lightmode = startupP.isLightTheme();

    }

    private void setTheme() {
        if (lightmode) {
            FlatLightLaf.setup();
        } else {
            FlatDarkLaf.setup();
        }
    }

    /**
     * Switches the theme. For that it unforetunately has to rebuild the entire UI
     */
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

    /**
     * Sets a JPanel as the main panel under the TopMenuBar
     * @param page
     */
    public void setMainWindowContent(JPanel page) {
        JPanel mainPanel = window.getMainPane();
        mainPanel.removeAll();
        mainPanel.add(page, BorderLayout.CENTER);

        mainPanel.repaint();
        mainPanel.revalidate();
    }

    /**
    * sets the window content as one of the predefined main window spaces
    * @param mode
    */
    public void setWindowContent(String mode) {
        System.out.println("setting to: " + mode);
        currentPage = mode;
        setMainWindowContent(pages.get(mode));
    }

    /**
     * sets the UserPfofilePage as an index-> selects the correct tab
     * @param userprofilePage
     * @param i
     */
    public void setWindowContent(String userprofilePage, int i) {
        setWindowContent(userprofilePage);
        if (userprofilePage == UIController.USERPROFILE_PAGE) {
            ((UserPage) pages.get(UIController.USERPROFILE_PAGE)).setTab(i);
        }
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

    public String getTranslatedString(String string) {
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
            return getDefaultFontColor();
        }
        if (budget < usedBudget) {
            return getNotAffortableColor();
        }
        if (budget * 0.9 < usedBudget) {
            return getCloseToSpendingLimitColor();
        }
        return getAffortableColor();
    }

    private Color getDefaultFontColor() {
        return defaultFontColor;
    }

    /**
     * sets the error message if a login fails
     * @param string
     */
    public void displayDeniedLoginMessage(String string) {
        if (currentPage.equals(LOGIN_PAGE)) {
            ((UserLoginPage) pages.get(LOGIN_PAGE)).setErrorMessage(string);
        }
    }

    /**
     * sets the theme as darktheme on true
     * @param setDarkTheme
     */
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

    /**
     * rebuilds the UserPanels (on User Login)
     */
    public void regenerateUserDefinedPanels() {
        pages = new THashMap<>();
        initializePages();
    }

    public Color getDefaultRedColor() {
        return defaultRedColor;
    }

    public boolean isLightTheme() {
        return lightmode;
    }

}
