package lib.uiComponents;

import java.awt.*;

import javax.swing.*;

public class rigitFreeSpace extends JPanel {
    Color color;

    public rigitFreeSpace(Color c, Dimension d) {
        color = c;
        if (c == null) {
            setOpaque(false);
        }
        addRigitComponent(d);
    }

    public void addRigitComponent(Dimension d) {
        Component fillComponent = Box.createRigidArea(d);
        fillComponent.setBackground(color);
        this.setBackground(color);
        this.add(fillComponent);
    }

    public void changeSize(Dimension d) {
        this.removeAll();
        addRigitComponent(d);
    }
}
