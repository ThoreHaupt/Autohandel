package GUI;

import javax.swing.*;
import java.awt.*;

import Controller.Controller;
import GUI.shopPage.ShopPage;
import GUI.topMenuBar.TopMenuBar;

public class Window extends JFrame {
    UIController uiController;
    Controller controller;
    Container ContentPane;

    public Window(Controller controller, UIController UIcontroller) {

        this.uiController = UIcontroller;
        this.controller = controller;
        ContentPane = this.getContentPane();
        ContentPane.add(createWindow());

        setBasics("test");
    }

    private JPanel createWindow() {
        JPanel panel = new JPanel(new BorderLayout());
        TopMenuBar menu = new TopMenuBar(controller, uiController, new Dimension(this.getWidth(), 60));
        panel.add(menu, BorderLayout.NORTH);

        panel.add(new ShopPage(uiController), BorderLayout.CENTER);
        panel.setBackground(uiController.getDefaultBackgroundcolor());
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
