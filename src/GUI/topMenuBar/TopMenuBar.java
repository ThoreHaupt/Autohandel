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
    UIController UIcontroller;

    int min_Height = 50;
    int width;
    int componentWidth = 0;

    /**
     * 
     */
    public TopMenuBar(Controller controller, UIController uiController, Dimension dim) {
        super(new FlowLayout());
        this.c = controller;
        this.UIcontroller = uiController;
        this.min_Height = dim.height;
        this.width = dim.width;

        this.add(new ImageButton("resources/GUI_images/CarImage2.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                uiController.setWindowContent("store");
            }

        }, new Dimension(130, min_Height)));

        this.add(new rigitFreeSpace(UIcontroller, new Dimension(200, min_Height)));

        this.add(new Searchbar(c, uiController));

        this.add(new rigitFreeSpace(UIcontroller, new Dimension(70, min_Height)));

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

    public Component addReal(Component c) {
        super.add(c);
        componentWidth += c.getWidth();
        return c;
    }

}
