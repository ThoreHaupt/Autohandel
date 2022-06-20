package lib.Other;

import java.awt.Image;
import java.awt.Dimension;

import javax.swing.ImageIcon;

public class ImageTools {
    public static ImageIcon resizeImageIcon(ImageIcon imageIcon, Dimension n) {
        Image rawImage = imageIcon.getImage();
        Image scaledImage = rawImage.getScaledInstance(
                (int) n.getWidth(),
                (int) n.getHeight(),
                Image.SCALE_FAST);
        return new ImageIcon(scaledImage);
    }
}
