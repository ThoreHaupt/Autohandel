package lib.uiComponents;

import java.awt.Dimension;

import javax.swing.ImageIcon;

/**
 * Image Rotate Button. So a press will switch to the next Image and select next Index
 */
public class IRButton extends ImageButton {

    ImageIcon[] images;
    int imageCount;
    int currentImageIndex;

    public IRButton(String[] imagePaths, Dimension n) {
        super(imagePaths[0], null, n);
        addActionListener(e -> nextImage());
        images = getImageArrayList(imagePaths, n);
    }

    /**
     * initializes all imageIcons from the Paths
     * @param imagePaths 
     * @param n
     * @return
     */
    private ImageIcon[] getImageArrayList(String[] imagePaths, Dimension n) {
        ImageIcon[] arr = new ImageIcon[imagePaths.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new ImageIcon(imagePaths[i]);
        }
        return arr;
    }

    /**
     * sets next Index as current index and then changes the Icon
     */
    public void nextImage() {
        currentImageIndex = ++currentImageIndex % imageCount;
        setIconByIndex(currentImageIndex);
    }

    public void setIconByIndex(int i) {
        setImageIcon(images[i]);
    }

    public int getSelectedIndex() {
        return currentImageIndex;
    }

    public int getCurrentIndex() {
        return 0;
    }
}
