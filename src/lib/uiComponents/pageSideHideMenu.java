package lib.uiComponents;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class pageSideHideMenu extends JPanel {

    private JPanel sideMenu;
    private JPanel mainPage;
    private JPanel hiddenMainPage;

    private ImageButton menuButton;

    private boolean isExtended = false;

    public pageSideHideMenu(int menuHorizontalSize) {
        this.maxMenuSize = menuHorizontalSize;
        createPanel();

    }

    public void createPanel() {
        sideMenu = new JPanel();
        sideMenu.setBackground(new Color(255, 0, 0));
        sideMenu.setMaximumSize(new Dimension(maxMenuSize, 50000));
        sideMenu.setPreferredSize(new Dimension(maxMenuSize, 1000));

        hiddenMainPage = new JPanel();
        mainPage = new JPanel();
        mainPage.setBackground(new Color(255, 255, 0));

        setLayout(new BorderLayout());
        hiddenMainPage.setLayout(new BorderLayout());

        menuButton = new ImageButton("resources/GUI_images/menuSideButton.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSideVisibility(!isExtended);
            }

        }, new Dimension(60, 60));

        // menuButton.setBorder(new RoundedBorder(15));
        menuButton.setBackground(new Color(0, 255, 255));

        // puts ImageButton in top left Corner, right next to the ede to the menu
        JPanel mask = new JPanel();
        mask.setBackground(new Color(0, 0, 250));
        // mask.setOpaque(true);
        mask.setLayout(new BorderLayout());

        JPanel mask2 = new JPanel();
        mask2.setLayout(new BorderLayout());
        mask2.add(menuButton, BorderLayout.NORTH);
        mask2.setMaximumSize(new Dimension(60, 10000));
        mask2.setBackground(new Color(0, 255, 0));

        mask.add(mask2, BorderLayout.WEST);

        hiddenMainPage.add(mainPage, BorderLayout.CENTER);
        hiddenMainPage.add(mask, BorderLayout.CENTER);
        hiddenMainPage.setBackground(new Color(100, 100, 100));
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
