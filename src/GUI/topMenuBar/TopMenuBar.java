package GUI.topMenuBar;

//import flatleaf;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Controller.Controller;
import GUI.UIController;
import LocalizationLogic.Language;
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

        //add(createMiddelSide(), BorderLayout.CENTER);
        add(createLeftSide(), BorderLayout.WEST);
        add(createRightSide(), BorderLayout.EAST);

        setBackground(uiController.getDefaultBackgroundcolor());
        setPreferredSize(new Dimension(0, min_Height + 10));
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

        MLButton b = new MLButton(uiController, "switch Theme");
        b.addActionListener(e -> uiController.switchTheme());
        panel.add(b);

        // directs you to the cart
        ImageButton cartButton = new ImageButton("resources/GUI_images/basket.png",
                e -> uiController.setWindowContent(UIController.USERPROFILE_PAGE, 0), new Dimension(60, min_Height));
        cartButton.setBackground(uiController.getDefaultBackgroundcolor());
        panel.add(cartButton);

        // Button that sends you to the User menu / login screen
        ImageButton userMenuButton = new ImageButton("resources/GUI_images/Sample_User_Icon.png",
                e -> uiController.getController().UserProfileButtonRequest(), new Dimension(60, min_Height));
        userMenuButton.setBackground(uiController.getDefaultBackgroundcolor());
        panel.add(userMenuButton);

        // lets you select the language
        ImageComboBox lanugageSelection = new ImageComboBox(uiController, Language.getLanuguageImageArray(),
                e -> controller.setLanguage(((ImageComboBox) e.getSource()).getSelectedIndex()),
                new Dimension(60, min_Height));
        lanugageSelection.setSelectedIndex(controller.lc.getCurrentLanguage().getIndex());
        lanugageSelection.setBackground(uiController.getDefaultBackgroundcolor());
        panel.add(lanugageSelection);
        return panel;
    }

}
