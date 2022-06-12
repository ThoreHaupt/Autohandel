package GUI;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.*;

import java.awt.*;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import Controller.Controller;
import GUI.cartPage.CartPage;
import GUI.payProcessPage.PayProcess;
import GUI.shopPage.ShopPage;
import GUI.topMenuBar.TopMenuBar;
import GUI.userPage.UserPage;

public class UIController {

    Window window;
    int windowWidth;
    int windowheight;

    public static final String MAINSTORE_PAGE = "store";
    public static final String CART_PAGE = "cart";
    public static final String PAYMENT_PAGE = "payment";
    public static final String USERPROFILE_PAGE = "userProfile";

    JPanel topMenuBar;
    HashMap<String, JPanel> pages = new HashMap<>();

    boolean lightmode = false;

    private Controller controller;

    Color backGroudnColor = new Color(255, 255, 255);

    public UIController(Controller controller) {
        loadStartUpInfoFile();
        setTheme();

        this.controller = controller;

        window = new Window(this);
        topMenuBar = new TopMenuBar(this, new Dimension(window.getWidth(), 60));

        initializePages();
        initializeTopMenuBar();
        setMainWindowContent(pages.get(MAINSTORE_PAGE));
    }

    private void initializeTopMenuBar() {
        window.getContentPane().add(topMenuBar, BorderLayout.NORTH);
    }

    private void initializePages() {
        pages.put(MAINSTORE_PAGE, new ShopPage(this));
        pages.put(CART_PAGE, new CartPage(this));
        pages.put(PAYMENT_PAGE, new PayProcess(this));
        pages.put(USERPROFILE_PAGE, new UserPage(this));

    }

    /**
     * reads a startup file containing setting information for example
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
        setMainWindowContent(pages.get(mode));
    }

    public Color getDefaultUIColor() {
        return null;
    }

    public Color getDefaultBackgroundcolor() {
        return null;
    }

    public Controller getController() {
        return controller;
    }

    public Font getDefaultFont() {
        return new Font("Segoe", Font.PLAIN, 20);
    }

    public Color getDefaultAccentColor() {
        return new Color(75, 150, 255);
    }

    public void setMainWindowContent(JPanel page) {
        JPanel mainPanel = window.getMainPane();
        mainPanel.removeAll();
        mainPanel.add(page, BorderLayout.CENTER);
        mainPanel.revalidate();
    }
}
