package GUI.userPage;

import javax.swing.JPanel;
import java.awt.*;

import Controller.Controller;
import GUI.UIController;
import lib.uiComponents.MLButton;

/**
 * The user Page initially thought to contain settings for the user and their preferences, but in an effort 
 * to finish the project on time this was skipped.
 * Now this only contains the log of button
 */
public class UserProfilePage extends JPanel {
    UIController uiController;
    Controller controller;

    /**
     * constructs the User Profile page
     * @param uiController
     */
    public UserProfilePage(UIController uiController) {
        this.uiController = uiController;
        this.controller = uiController.getController();
        buildProfilePage();
    }

    /**
     * builds all Components for the UserPage
     */
    void buildProfilePage() {
        this.add(buildLogOffButton());
    }

    /**
     * builds the log of Button
     * @return
     */
    JPanel buildLogOffButton() {
        JPanel panel = new JPanel();

        MLButton logOffButton = new MLButton(uiController, "Log Off");
        logOffButton.setBackground(uiController.getDefaultRedColor());

        logOffButton.addActionListener(e -> uiController.getController().logOffCurrentUser());
        logOffButton.setPreferredSize(new Dimension(400, 300));
        panel.add(logOffButton);
        return panel;
    }
}
