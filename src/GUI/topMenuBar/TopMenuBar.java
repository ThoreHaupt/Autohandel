package GUI.topMenuBar;

//import flatleaf;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.DimensionUIResource;

import Controller.Controller;
import GUI.UIController;
import lib.uiComponents.Searchbar;

public class TopMenuBar extends JPanel {

    Controller c;
    UIController UIcontroller;

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

        }), new Dimension(200, 40));
        this.add(new Searchbar(c, UIcontroller));
        this.add(new ImageButton("resources/GUI_images/basket.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UIcontroller.setWindowContent("cart");
            }

        }));

        setBackground(new Color(255, 255, 255));
        setMinimumSize(new Dimension(0, 100));
    }

}
