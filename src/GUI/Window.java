package GUI;

import javax.swing.*;
import javax.swing.text.AbstractDocument.Content;

import java.awt.*;

import Controller.Controller;
import GUI.shopPage.ShopPage;
import GUI.topMenuBar.TopMenuBar;

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
        ContentPane.add(mainPanel, BorderLayout.CENTER);
        setBasics("CarShop");
    }

    private JPanel createWindow() {
        JPanel panel = new JPanel(new BorderLayout());
        TopMenuBar menu = new TopMenuBar(uiController, new Dimension(this.getWidth(), 60));
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

    public JPanel getMainPane() {
        return mainPanel;
    }
}
