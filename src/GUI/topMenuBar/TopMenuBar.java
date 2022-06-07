package GUI.topMenuBar;

//import flatleaf;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Controller.Controller;
import GUI.UIController;
import lib.uiComponents.*;

public class TopMenuBar extends JPanel {

    Controller c;
    UIController uiController;

    int min_Height = 70;
    int width;
    int componentWidth = 0;

    /**
     * 
     */
    public TopMenuBar(Controller controller, UIController uiController, Dimension dim) {
        super(new FlowLayout());
        this.c = controller;
        this.uiController = uiController;
        this.min_Height = dim.height;
        this.width = dim.width;

        this.add(new ImageButton("resources/GUI_images/CarImage2.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                uiController.setWindowContent("store");
            }

        }, new Dimension(130, min_Height)));

        this.add(new rigitFreeSpace(uiController, new Dimension(180, min_Height)));

        this.add(new Searchbar(c, uiController));

        this.add(new rigitFreeSpace(uiController, new Dimension(60, min_Height)));

        this.add(new ImageButton("resources/GUI_images/basket.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                uiController.setWindowContent("cart");
            }

        }, new Dimension(50, min_Height)));

        this.add(new ImageButton("resources/GUI_images/Sample_User_Icon.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                uiController.setWindowContent("userProfile");
            }

        }, new Dimension(50, min_Height)));

        this.add(new ImageComboBox(uiController, new String[] { "resources/GUI_images/IconUS.png",
                "resources/GUI_images/IconGer.png" }, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.setLanguage(((ImageComboBox) e.getSource()).getSelectedIndex());
                    }

                }, new Dimension(60, min_Height)));

        setBackground(uiController.getBackGroundColor());
        setPreferredSize(new Dimension(0, min_Height));
    }

    public JPanel createLeftSide() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        panel.add(new ImageButton("resources/GUI_images/CarImage2.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                uiController.setWindowContent("store");
            }

        }, new Dimension(130, min_Height)));

        return panel;
    }

    public JPanel createMiddelSide() {
        JPanel panel = new JPanel();
        return panel;
    }

    public JPanel createRightSide() {
        JPanel panel = new JPanel();
        panel.add(new Searchbar(c, uiController));
        return panel;
    }

    public Component addReal(Component c) {
        super.add(c);
        componentWidth += c.getWidth();
        return c;
    }

}
