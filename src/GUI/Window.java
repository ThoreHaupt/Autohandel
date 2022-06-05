package GUI;

import javax.swing.*;
import java.awt.*;

import Controller.Controller;
import GUI.topMenuBar.TopMenuBar;

public class Window extends JFrame {
    UIController UIcontroller;
    Controller controller;
    Container ContentPane;

    public Window(Controller controller, UIController UIcontroller) {

        this.UIcontroller = UIcontroller;
        this.controller = controller;
        ContentPane = this.getContentPane();
        ContentPane.add(createWindow());

        setBasics("test");
    }

    private JPanel createWindow() {
        JPanel panel = new JPanel(); // new GridLayout(2, 1)

        TopMenuBar menu = new TopMenuBar(controller, UIcontroller);
        panel.add(menu);
        panel.setBackground(UIcontroller.getBackGroudnColor());
        return panel;
    }

    private void setBasics(String windowTitle) {
        this.setTitle(windowTitle);
        this.setSize(1000, 500);
        // this.setBackground(new Color(1, 1, 1));
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
