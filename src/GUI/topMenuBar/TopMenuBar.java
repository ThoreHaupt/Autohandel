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

    int min_Height = 80;

    /**
     * 
     */
    public TopMenuBar(Controller controller, UIController UIcontroller) {
        super(new FlowLayout());
        this.c = controller;
        this.UIcontroller = UIcontroller;

        this.add(new ImageButton("resources/GUI_images/CarImage2.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UIcontroller.setWindowContent("store");
            }

        }, new Dimension(200, min_Height)));

        Component fillComponent = Box.createRigidArea(new Dimension(200, min_Height));
        fillComponent.setBackground(UIcontroller.getBackGroundColor());
        this.add(fillComponent);

        this.add(new Searchbar(c, UIcontroller));

        this.add(new ImageButton("resources/GUI_images/basket.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UIcontroller.setWindowContent("cart");
            }

        }, new Dimension(50, min_Height)));

        this.add(new ImageButton("resources/GUI_images/Sample_User_Icon.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UIcontroller.setWindowContent("userProfile");
            }

        }, new Dimension(50, min_Height)));

        this.add(new ImageComboBox("resources/GUI_images/basket.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setLanguage(((ImageComboBox) e.getSource()).getSelectedIndex());
            }

        }, new Dimension(50, min_Height)));

        setBackground(UIcontroller.getBackGroundColor());
        setMinimumSize(new Dimension(0, min_Height));
    }

}
