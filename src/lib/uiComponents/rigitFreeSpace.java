package lib.uiComponents;

import java.awt.*;

import javax.swing.*;

public class rigitFreeSpace extends JPanel {
    Color color;

    public rigitFreeSpace(Color c, Dimension d) {
        color = c;
        color = new Color(255, 255, 255);
        if (c == null) {
            setOpaque(false);
        }
        addRigitComponent(d);
    }

    public void addRigitComponent(Dimension d) {
        Component fillComponent = Box.createRigidArea(d);
        this.setPreferredSize(d);
        fillComponent.setBackground(color);
        this.setBackground(color);
        this.add(fillComponent);
    }

    public void changeSize(Dimension d) {
        this.removeAll();
        addRigitComponent(d);
        this.setPreferredSize(d);
    }
}
