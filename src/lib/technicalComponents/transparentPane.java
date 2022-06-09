package lib.technicalComponents;

import java.awt.*;
import javax.swing.*;

public class transparentPane extends JPanel {

    /**
     * 
     */
    public transparentPane() {
        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        // g.setColor(new Color(255, 0, 0, 64));
        // Insets insets = getInsets();
        // g.fillRect(insets.left, insets.top,
        // getWidth() - insets.left - insets.right,
        // getHeight() - insets.top - insets.bottom);
        // super.paintComponent(g);
    }

}
