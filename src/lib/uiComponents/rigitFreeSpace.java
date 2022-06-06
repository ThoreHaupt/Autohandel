package lib.uiComponents;

import java.awt.*;

import javax.swing.Box;
import javax.swing.JPanel;

import GUI.UIController;

public class rigitFreeSpace extends JPanel {
    UIController uiController;

    public rigitFreeSpace(UIController c, Dimension d) {
        uiController = c;
        addRigitComponent(d);
    }

    public void addRigitComponent(Dimension d) {
        Component fillComponent = Box.createRigidArea(d);
        fillComponent.setBackground(uiController.getBackGroundColor());
        this.setBackground(uiController.getBackGroundColor());
        this.add(fillComponent);
    }

    public void changeSize(Dimension d) {
        this.removeAll();
        addRigitComponent(d);
    }
}
