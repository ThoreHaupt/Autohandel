package GUI.topMenuBar;

import java.awt.*;
import java.awt.event.*;

import javax.crypto.MacSpi;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.DimensionUIResource;
//import swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.plaf.FontUIResource;

import Controller.Controller;
import GUI.UIController;

public class TopMenuBar extends JPanel {

    Controller c;
    UIController UIcontroller;

    /**
     * 
     */
    public TopMenuBar(Controller controller, UIController UIcontroller) {
        super(new FlowLayout());
        this.c = controller;
        this.UIcontroller = UIcontroller;
        this.add(createLogoButton());
        this.add(createSearchBar());
        setBackground(new Color(255, 255, 255));
        setMinimumSize(new Dimension(0, 100));
    }

    private JPanel createSearchBar() {
        JPanel panel = new JPanel();
        Font font = new Font("SANS_SERIF", Font.PLAIN, 18);
        String defaultText = c.lc.s("search database");
        panel.setMinimumSize(new DimensionUIResource(500, 500));
        JTextField textField = new JTextField(30);
        textField.setFont(font);
        // textField.setSize(new DimensionUIResource(500, 90));
        textField.setText(defaultText);
        textField.setEditable(true);

        textField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (textField.getText().equals(defaultText))
                    textField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().equals(""))
                    textField.setText(defaultText);
            }

        });

        JButton searchButton = new JButton(c.lc.s("Go"));
        searchButton.setBackground(new Color(255, 0, 0));
        // searchButton.setBorder(BorderFactory.createEmptyBorder());
        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                c.searchShop(textField.getText());
                UIcontroller.setWindowContent("store");
                textField.setText(defaultText);
            }

        };
        JComboBox<String> completions = new JComboBox<>();
        searchButton.addActionListener(actionListener);
        textField.addActionListener(actionListener);

        // AutoCompleteDecorator decorator;
        panel.add(textField);
        panel.add(searchButton);
        panel.setBackground(new Color(255, 255, 255));
        return panel;
    }

    private JPanel createLogoButton() {
        JPanel logoPane = new JPanel();
        // "resources/GUI_images/CarImage2.png"
        ImageIcon buttonIcon;
        /*
         * try {
         * buttonIcon = ImageIO.read(new File("resources/GUI_images/CarImage2.png"));
         * ImageIcon icon = new ImageIcon(buttonIcon);
         * } catch (IOException e) {
         * buttonIcon = new ImageIcon("hallo");
         * System.out.println("Icon Image not found");
         * }
         */
        buttonIcon = new ImageIcon("resources/GUI_images/CarImage2.png");
        JButton button = new JButton();
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UIcontroller.setWindowContent("store");
            }

        });
        button.setIcon(buttonIcon);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        logoPane.add(button);
        logoPane.setBackground(new Color(255, 255, 255));
        return logoPane;

    }

}
