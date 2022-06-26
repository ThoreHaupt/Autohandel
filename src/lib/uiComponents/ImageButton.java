package lib.uiComponents;

import javax.swing.*;

import lib.Other.ImageTools;

import java.awt.event.*;
import java.awt.*;

/**
 * Coustom UI Component that is a button, but it is just the image and the border
 */
public class ImageButton extends JButton {
    String image_path;
    ImageIcon buttonIcon;
    Dimension buttonDimension;

    public ImageButton(String image_path, ActionListener l) {
        createButton(image_path, l, new Dimension(200, 40));
    }

    public ImageButton(String image_path, ActionListener l, Dimension n) {
        createButton(image_path, l, n);
        this.buttonDimension = n;
    }

    private void createButton(String image_path, ActionListener l, Dimension n) {
        buttonIcon = new ImageIcon(image_path);
        this.image_path = image_path;
        setIcon(buttonIcon);
        setPreferredSize(n);
        // setBorder(BorderFactory.createEmptyBorder());
        // setContentAreaFilled(false);
        addActionListener(l);
    }

    public void resizeImageButton(Dimension dimension) {
        setPreferredSize(dimension);
        this.setIcon(ImageTools.resizeImageIcon(buttonIcon, dimension));
        revalidate();
        repaint();
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.setIcon(ImageTools.resizeImageIcon(imageIcon, buttonDimension));
        revalidate();
        repaint();
    }

}
