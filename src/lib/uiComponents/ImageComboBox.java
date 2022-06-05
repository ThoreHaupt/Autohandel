package lib.uiComponents;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ImageComboBox extends JComboBox<ImageIcon> {
    public ImageComboBox(String image_path, ActionListener l) {
        createImageComboBox(image_path, l, new Dimension(200, 40));
    }

    public ImageComboBox(String image_path, ActionListener l, Dimension n) {
        createImageComboBox(image_path, l, n);
    }

    private void createImageComboBox(String image_path, ActionListener l, Dimension n) {
    }

}
