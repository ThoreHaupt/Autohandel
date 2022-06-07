package GUI;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.awt.*;

import Controller.Controller;
import GUI.shopPage.ShopPage;
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
        JPanel panel = new JPanel(new BorderLayout());
        TopMenuBar menu = new TopMenuBar(controller, UIcontroller, new Dimension(this.getWidth(), 80));
        panel.add(menu, BorderLayout.NORTH);

        panel.add(new ShopPage(), BorderLayout.CENTER);
        panel.setBackground(UIcontroller.getBackGroundColor());
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
