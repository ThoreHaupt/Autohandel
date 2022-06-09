package lib.uiComponents;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

import lib.technicalComponents.transparentPane;

import java.awt.*;
import java.awt.event.*;

public class pageSideHideMenu extends JPanel {

    private JPanel sideMenu;
    private JPanel mainPage;
    private JPanel hiddenMainPage;

    private ImageButton menuButton;
    private int buttonSize = 30;

    private boolean isExtended = false;

    public pageSideHideMenu(JPanel mainPage, JPanel sideMenu, int menuHorizontalSize) {
        this.maxMenuSize = menuHorizontalSize;
        this.sideMenu = sideMenu;
        this.mainPage = mainPage;
        createPanel();

    }

    public void createPanel() {

        setLayout(new BorderLayout());

        sideMenu.setMaximumSize(new Dimension(maxMenuSize, 50000));
        sideMenu.setPreferredSize(new Dimension(maxMenuSize, 1000));

        hiddenMainPage = new JPanel();
        hiddenMainPage.setLayout(new BorderLayout());
        hiddenMainPage.setOpaque(false);

        // Button to extend the left Menu
        menuButton = new ImageButton("resources/GUI_images/menuSideButton.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSideVisibility(!isExtended);
            }

        }, new Dimension(buttonSize, buttonSize));

        // puts ImageButton in top left Corner, right next to the ede to the menu
        transparentPane mask = new transparentPane();
        mask.setLayout(new BorderLayout());
        mask.setOpaque(false);

        transparentPane mask2 = new transparentPane();
        mask2.setLayout(new BorderLayout());
        mask2.setOpaque(false);

        transparentPane mask3 = new transparentPane();
        mask3.setLayout(new GridLayout(2, 1));
        mask3.setOpaque(false);

        mask3.add(new rigitFreeSpace(null, new Dimension(buttonSize, 10)));
        mask3.add(menuButton);
        mask2.add(mask3, BorderLayout.NORTH);

        mask2.setMaximumSize(new Dimension(60, 10000));

        mask.add(mask2, BorderLayout.WEST);

        hiddenMainPage.add(mainPage, BorderLayout.CENTER);
        hiddenMainPage.add(mask, BorderLayout.WEST);

        transparentPane testPane = new transparentPane();
        testPane.setOpaque(true);
        testPane.setBackground(new Color(255, 255, 255, 0));
        // hiddenMainPage.add(testPane, BorderLayout.WEST);
        add(hiddenMainPage);
    }

    public void setSideVisibility(boolean visible) {
        if (visible == isExtended)
            return;
        if (visible) {
            showSide();
        } else {
            hideSide();
        }
    }

    private void showSide() {
        isExtended = true;
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(sideMenu, BorderLayout.WEST);
        this.add(hiddenMainPage, BorderLayout.CENTER);
        this.revalidate();
    }

    private void hideSide() {
        isExtended = false;
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(hiddenMainPage, BorderLayout.CENTER);
        this.revalidate();

    }

    /**
     * @param maxMenuSize the maxMenuSize to set
     */
    public void setMaxMenuSize(int maxMenuSize) {
        this.maxMenuSize = maxMenuSize;
    }

    private int maxMenuSize;

    /**
     * @return the sideMenu
     */
    public JPanel getSideMenu() {
        return sideMenu;
    }

    /**
     * @return the mainPage
     */
    public JPanel getMainPage() {
        return mainPage;
    }

    /**
     * @return the isExtended
     */
    public boolean isExtended() {
        return isExtended;
    }
}
