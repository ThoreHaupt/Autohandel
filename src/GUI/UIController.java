package GUI;

import java.awt.Color;

import com.formdev.flatlaf.FlatLightLaf;

import Controller.Controller;

public class UIController {

    Window window;
    int windowWidth;
    int windowheight;

    Color backGroudnColor = new Color(255, 255, 255);

    /**
     * @return the backGroudnColor
     */
    public Color getBackGroundColor() {
        return backGroudnColor;
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        new UIController(new Controller());
    }

    public UIController(Controller controller) {
        window = new Window(controller, this);
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

    public Color getBackgroundcolor() {
        return null;
    }
}
