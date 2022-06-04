package GUI;

import Controller.Controller;

public class GUIThread extends Thread {

    /**
     * @param name
     */
    public GUIThread(String name, Controller c) {
        super(name);
        new UIController(c);
    }

}
