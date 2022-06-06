package lib.uiComponents;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ImageButton extends JButton {
    public ImageButton(String image_path, ActionListener l) {
        createButton(image_path, l, new Dimension(200, 40));
    }

    public ImageButton(String image_path, ActionListener l, Dimension n) {
        createButton(image_path, l, n);
    }

    private void createButton(String image_path, ActionListener l, Dimension n) {
        ImageIcon buttonIcon = new ImageIcon(image_path);
        setIcon(buttonIcon);
        setPreferredSize(n);
        // setBorder(BorderFactory.createEmptyBorder());
        // setContentAreaFilled(false);
        addActionListener(l);
    }
}
