package GUI.userPage;

import javax.sound.midi.ControllerEventListener;
import javax.swing.JPanel;
import java.awt.*;

import Controller.Controller;
import GUI.UIController;
import lib.uiComponents.MLButton;

public class UserProfilePage extends JPanel {
    UIController uiController;
    Controller controller;

    public UserProfilePage(UIController uiController) {
        this.uiController = uiController;
        this.controller = uiController.getController();
        buildProfilePage();
    }

    void buildProfilePage() {
        this.add(buildLogOffButton());
    }

    JPanel buildLogOffButton() {
        JPanel panel = new JPanel();

        MLButton logOffButton = new MLButton(uiController, "Log Off");
        logOffButton.setBackground(new Color(171, 3, 3));

        logOffButton.addActionListener(e -> uiController.getController().logOffCurrentUser());
        logOffButton.setPreferredSize(new Dimension(400, 300));
        panel.add(logOffButton);
        return panel;
    }
}
