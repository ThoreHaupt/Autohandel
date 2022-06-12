package GUI;

import javax.swing.*;

import java.awt.*;

import Controller.Controller;

public class Window extends JFrame {
    UIController uiController;
    Controller controller;
    Container ContentPane;
    JPanel mainPanel;

    public Window(UIController UIcontroller) {

        this.uiController = UIcontroller;
        this.controller = UIcontroller.getController();
        ContentPane = this.getContentPane();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(uiController.getDefaultBackgroundcolor());
        ContentPane.add(mainPanel, BorderLayout.CENTER);
        setBasics("CarShop");
    }

    private void setBasics(String windowTitle) {
        this.setTitle(windowTitle);
        this.setSize(1000, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JPanel getMainPane() {
        return mainPanel;
    }
}
