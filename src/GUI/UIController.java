package GUI;

import java.awt.Color;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

import java.awt.*;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import Controller.Controller;

public class UIController {

    Window window;
    int windowWidth;
    int windowheight;

    boolean lightmode = false;

    private Controller controller;

    Color backGroudnColor = new Color(255, 255, 255);

    public UIController(Controller controller) {
        loadStartUpInfoFile();
        setTheme();
        this.controller = controller;
        window = new Window(controller, this);
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
        switch (mode) {
            case "store":
                System.out.println("going to Store");
                break;
            case "cart":
                break;
            case "payment":
                break;
            case "userProfile":
                break;
            default:
                break;
        }
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
        return new Color(0, 0, 255);
    }
}
