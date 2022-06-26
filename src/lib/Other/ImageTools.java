package lib.Other;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageTools {

    public final static String defaultNoImagePath = "resources/GUI_images/no_ImageImage.png";

    /**
     * returns an image Icon with the specified Dimension. However I dont know why, when I used the same input reference to the Image, it changed 
     * size once a new Image with a different size was created. --> in Product I have a list with 3 images
     * @param imageIcon
     * @param n
     * @return
     */
    public static ImageIcon resizeImageIcon(ImageIcon imageIcon, Dimension n) {
        Image rawImage = imageIcon.getImage();
        Image scaledImage = rawImage.getScaledInstance(
                (int) n.getWidth(),
                (int) n.getHeight(),
                Image.SCALE_FAST);
        return new ImageIcon(scaledImage);
    }

    /**
     * gets the ImageIcon for any String, independent of wether it is a download or the file is local.
     * @param path the path of the Image
     * @return the Image Icon with the image of the specified path
     */
    public static ImageIcon getIconFromAnyLocation(String path) {
        ImageIcon icon;
        if (path.substring(0, 4).equals("http")) {
            try {
                URL url = new URL(path);
                BufferedImage image = ImageIO.read(url);
                if (image == null) {
                    return new ImageIcon(path);
                }
                icon = new ImageIcon(image);
            } catch (MalformedURLException e1) {
                System.out.println("a image is not avaliable anymore");
                icon = new ImageIcon(defaultNoImagePath);
            } catch (IOException e) {
                System.out.println("Something went wrong with this image");
                icon = new ImageIcon(defaultNoImagePath);
            }

        } else {
            icon = new ImageIcon(path);
        }
        return icon;
    }
}
