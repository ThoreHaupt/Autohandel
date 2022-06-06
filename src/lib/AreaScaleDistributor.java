package lib;

import java.util.ArrayList;

import GUI.UIController;
import java.awt.*;

public class AreaScaleDistributor {

    UIController uiController;
    Direction scaleDirection;
    Dimension realDim;
    Dimension usedDim;
    ArrayList<Component> components;
    ArrayList<Double> targetScale;

    /**
     * @param c
     * @param uiController
     */
    public AreaScaleDistributor(UIController uiController, Direction dir, Dimension dim) {
        this.uiController = uiController;
    }

    public enum Direction {
        HORIZONTAL,
        VERTICAL;
    }

    public void addComponent(Component e, int index) {
        components.add(e);
        if (scaleDirection == Direction.HORIZONTAL) {
            usedDim.width += e.getWidth();
        }

        refresh();
    }

    public void removeComponent(int index) {
        components.remove(index);
        refresh();
    }

    public void setSize(Dimension dim) {

    }

    public void refresh() {

        Dimension d = (Dimension) usedDim.clone();
        if (scaleDirection == Direction.HORIZONTAL) {
            for (int i = 0; i < components.size(); i++) {
                d.width = (int) ((components.get(i).getWidth() / usedDim.width) * realDim.getWidth());
                components.get(i).setPreferredSize(d);
            }
        } else {

        }

    }

}
