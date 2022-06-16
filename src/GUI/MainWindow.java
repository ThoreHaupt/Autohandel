package GUI;

import javax.swing.*;
import javax.swing.event.EventListenerList;

import java.awt.*;
import java.awt.event.*;

import Controller.Controller;
import lib.Event.WindowSizeChangeEvent;
import lib.Event.WindowSizeChangeListener;

public class MainWindow extends JFrame {
    UIController uiController;
    Controller controller;
    Container ContentPane;
    JPanel mainPanel;

    protected EventListenerList windowChangeListenerList = new EventListenerList();

    public MainWindow(UIController UIcontroller) {

        this.uiController = UIcontroller;
        this.controller = UIcontroller.getController();
        ContentPane = this.getContentPane();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(uiController.getDefaultBackgroundcolor());
        ContentPane.add(mainPanel, BorderLayout.CENTER);
        setEvents();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setBasics("CarShop");
    }

    private void setEvents() {
        // when someonepresses the maximize Button this event fires
        this.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                fire_onWindowSizeChange(new WindowSizeChangeEvent(this));
            }
        });

        // when someone changes the window size manually
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                fire_onWindowSizeChange(new WindowSizeChangeEvent(this));
            }

        });
    }

    public void addWindowSizeChangeListener(WindowSizeChangeListener listener) {
        windowChangeListenerList.add(WindowSizeChangeListener.class, listener);
    }

    public void removeWindowSizeChangeListener(WindowSizeChangeListener listener) {
        windowChangeListenerList.remove(WindowSizeChangeListener.class, listener);
    }

    void fire_onWindowSizeChange(WindowSizeChangeEvent event) {
        Object[] listeners = windowChangeListenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == WindowSizeChangeListener.class) {
                ((WindowSizeChangeListener) listeners[i + 1]).windowSizeChanged(event);
            }
        }
    }

    private void setBasics(String windowTitle) {
        this.setTitle(windowTitle);
        this.setSize(1000, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JPanel getMainPane() {
        return mainPanel;
    }
}
