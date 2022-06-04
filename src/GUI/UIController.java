package GUI;

import Controller.Controller;

public class UIController {

    Window window;
    int windowWidth;
    int windowheight;

    public static void main(String[] args) {
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
}
