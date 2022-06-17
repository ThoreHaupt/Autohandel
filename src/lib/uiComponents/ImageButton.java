package lib.uiComponents;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ImageButton extends JButton {
    String image_path;
    ImageIcon buttonIcon;

    public ImageButton(String image_path, ActionListener l) {
        createButton(image_path, l, new Dimension(200, 40));
    }

    public ImageButton(String image_path, ActionListener l, Dimension n) {
        createButton(image_path, l, n);
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
        Image rawImage = buttonIcon.getImage();
        Image scaledImage = rawImage.getScaledInstance(
                (int) dimension.getWidth(),
                (int) dimension.getHeight(),
                Image.SCALE_FAST);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        setPreferredSize(dimension);
        this.setIcon(scaledImageIcon);
        revalidate();
        repaint();
    }
}
