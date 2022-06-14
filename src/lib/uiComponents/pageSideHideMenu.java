package lib.uiComponents;

import javax.swing.*;
import javax.swing.event.EventListenerList;

import com.formdev.flatlaf.util.ColorFunctions;

import GUI.UIController;
import lib.Event.SideHideExtentionStateChangeEvent;
import lib.Event.SideHideExtentionStateChangeListener;
import lib.technicalComponents.transparentPane;

import java.awt.*;
import java.awt.event.*;

public class PageSideHideMenu extends JPanel {

    private JPanel sideMenu;
    private JPanel mainPage;
    private JPanel hiddenMainPage;

    private ImageButton menuButton;
    private int buttonSize = 30;

    private boolean isExtended = false;

    protected EventListenerList listenerList = new EventListenerList();

    public PageSideHideMenu(JPanel mainPage, JPanel sideMenu, int menuHorizontalSize) {
        this.maxMenuSize = menuHorizontalSize;
        this.sideMenu = sideMenu;
        this.mainPage = mainPage;
        createPanel();

    }

    public void createPanel() {

        setLayout(new BorderLayout());

        sideMenu.setPreferredSize(new Dimension(maxMenuSize, 1000));
        sideMenu.setBackground(new Color(255, 0, 0));

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
        JPanel mask = new transparentPane();
        mask.setLayout(new BorderLayout());
        mask.setBackground(new Color(0, 255, 255));

        JPanel mask2 = new transparentPane();
        mask2.setLayout(new BorderLayout());
        mask2.setOpaque(false);

        JPanel mask3 = new transparentPane();
        mask3.setLayout(new GridLayout(2, 1));
        mask3.setOpaque(false);

        mask3.add(new rigitFreeSpace(null, new Dimension(buttonSize, 10)));
        mask3.add(menuButton);
        mask2.add(mask3, BorderLayout.NORTH);

        mask2.setMaximumSize(new Dimension(60, 10000));

        mask.add(mask2, BorderLayout.WEST);

        hiddenMainPage.add(mainPage, BorderLayout.EAST);
        hiddenMainPage.add(mask, BorderLayout.WEST);

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
        fireExtentionStateChange(new SideHideExtentionStateChangeEvent(this));
    }

    private void hideSide() {
        isExtended = false;
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(hiddenMainPage, BorderLayout.CENTER);
        this.revalidate();
        fireExtentionStateChange(new SideHideExtentionStateChangeEvent(this));

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

    void fireExtentionStateChange(SideHideExtentionStateChangeEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == SideHideExtentionStateChangeListener.class) {
                ((SideHideExtentionStateChangeListener) listeners[i + 1]).extentionStateChanged(event);
            }
        }
    }

    public void addSideHideExtentionStateChangeListener(SideHideExtentionStateChangeListener listener) {
        listenerList.add(SideHideExtentionStateChangeListener.class, listener);
    }

    public void removeSideHideExtentionStateChangeListener(SideHideExtentionStateChangeListener listener) {
        listenerList.remove(SideHideExtentionStateChangeListener.class, listener);
    }
}
