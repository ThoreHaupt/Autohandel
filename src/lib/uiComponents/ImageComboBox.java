package lib.uiComponents;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;

import GUI.UIController;

import java.awt.event.*;
import java.util.Arrays;
import java.awt.*;

public class ImageComboBox extends JComboBox<ImageIcon> {
    private UIController uiController;

    public ImageComboBox(UIController uiController, String image_path, ActionListener l) {
        this.uiController = uiController;
        createImageComboBox(new String[] { image_path }, l, new Dimension(200, 80));
    }

    public ImageComboBox(UIController uiController, String[] image_path_Array, ActionListener l) {
        this.uiController = uiController;
        createImageComboBox(image_path_Array, l, new Dimension(200, 80));
    }

    public ImageComboBox(UIController uiController, String image_path, ActionListener l, Dimension n) {
        this.uiController = uiController;
        createImageComboBox(new String[] { image_path }, l, n);
    }

    public ImageComboBox(UIController uiController, String[] image_path_Array, ActionListener l, Dimension n) {
        this.uiController = uiController;
        createImageComboBox(image_path_Array, l, n);
    }

    private void createImageComboBox(String[] image_path, ActionListener l, Dimension n) {
        removeSelectorButton();
        setImageSet(image_path);
        this.addActionListener(l);
        this.setPreferredSize(n);
        this.setSelectedIndex(0);
        setBackground(uiController.getBackGroundColor());
        setBorder(BorderFactory.createEmptyBorder());

    }

    public void setImageSet(String[] paths) {
        ImageIcon[] imageIcons = new ImageIcon[paths.length];
        for (int i = 0; i < imageIcons.length; i++) {
            imageIcons[i] = new ImageIcon(paths[i]);
        }
        DefaultComboBoxModel<ImageIcon> model = (DefaultComboBoxModel<ImageIcon>) this.getModel();
        model.removeAllElements();
        model.addAll(Arrays.asList(imageIcons));
    }

    private void removeSelectorButton() {
        this.setUI(new BasicComboBoxUI() {

            @Override
            protected JButton createArrowButton() {
                return new JButton() {

                    @Override
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        this.remove(this.getComponent(0));
    }

}
