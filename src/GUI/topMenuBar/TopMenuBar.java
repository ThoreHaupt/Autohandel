package GUI.topMenuBar;

//import flatleaf;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Controller.Controller;
import GUI.UIController;
import lib.uiComponents.*;

public class TopMenuBar extends JPanel {

    Controller controller;
    UIController uiController;

    int min_Height = 60;
    int width;
    int componentWidth = 0;

    /**
     * 
     */
    public TopMenuBar(UIController uiController, Dimension dim) {
        super(new FlowLayout());
        this.controller = uiController.getController();
        this.uiController = uiController;
        this.min_Height = dim.height;
        this.width = dim.width;

        setLayout(new BorderLayout());

        add(createLeftSide(), BorderLayout.WEST);
        add(createMiddelSide(), BorderLayout.CENTER);
        add(createRightSide(), BorderLayout.EAST);

        setBackground(uiController.getDefaultBackgroundcolor());
        setPreferredSize(new Dimension(0, min_Height));
    }

    public JPanel createLeftSide() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        panel.add(new ImageButton("resources/GUI_images/CarImage2_transparet.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                uiController.setWindowContent(UIController.MAINSTORE_PAGE);
            }

        }, new Dimension(130, min_Height)));

        return panel;
    }

    public JPanel createMiddelSide() {
        JPanel panel = new JPanel();
        panel.add(new Searchbar(controller, uiController));
        return panel;
    }

    public JPanel createRightSide() {
        JPanel panel = new JPanel();
        panel.add(new ImageButton("resources/GUI_images/basket.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                uiController.setWindowContent("cart");
            }

        }, new Dimension(60, min_Height)));

        panel.add(new ImageButton("resources/GUI_images/Sample_User_Icon.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                uiController.getController().UserProfileButtonRequest();
            }

        }, new Dimension(60, min_Height)));

        panel.add(new ImageComboBox(uiController, controller.getLanuguageImageArray(), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setLanguage(((ImageComboBox) e.getSource()).getSelectedIndex());
            }

        }, new Dimension(60, min_Height)));
        return panel;
    }

}
