package GUI;

import javax.swing.*;
import javax.swing.event.EventListenerList;

import java.awt.*;
import java.awt.event.*;

import Controller.Controller;
import lib.Event.WindowSizeChangeEvent;
import lib.Event.WindowSizeChangeListener;

/**
 * This is the main window on which everything is painted
 */
public class MainWindow extends JFrame {
    UIController uiController;
    Controller controller;
    Container ContentPane;
    JPanel mainPanel;

    protected EventListenerList windowChangeListenerList = new EventListenerList();

    /**
     * constructs the main window
     * by default the window maximizes. 
     * 
     * @param UIcontroller
     */
    public MainWindow(UIController UIcontroller) {

        this.uiController = UIcontroller;
        this.controller = UIcontroller.getController();
        initMainPane();
        setEvents();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setBasics("CarShop");
    }

    /**
     * initializes all the important things for the main Pane.
     * 
     */
    public void initMainPane() {
        ContentPane = this.getContentPane();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(uiController.getDefaultBackgroundcolor());
        ContentPane.add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * sets all the events that the main Window has to fire 
     * Mostly important on resize and on shutdown
     */
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

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                controller.intiShutDownSequence();
            }
        });
    }

    /**
     * 
     * @param listener
     */
    public void addWindowSizeChangeListener(WindowSizeChangeListener listener) {
        windowChangeListenerList.add(WindowSizeChangeListener.class, listener);
    }

    public void removeWindowSizeChangeListener(WindowSizeChangeListener listener) {
        windowChangeListenerList.remove(WindowSizeChangeListener.class, listener);
    }

    /**
     * the Listener that listens to when the window size changes
     * because there are two listeners which listen to either when someone presses the maximize button or change the window
     * size by dragging the edge, but this should cause the same action most of the time, so this is what fires when that is the case
     * @param event
     */
    void fire_onWindowSizeChange(WindowSizeChangeEvent event) {
        Object[] listeners = windowChangeListenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == WindowSizeChangeListener.class) {
                ((WindowSizeChangeListener) listeners[i + 1]).windowSizeChanged(event);
            }
        }
    }

    /**
     * sets the basics, like the window title and the window Icon in the taskbar.
     * Also this sets the window visible
     * and disables the default close process
     * @param windowTitle
     */
    private void setBasics(String windowTitle) {
        this.setTitle(windowTitle);

        setIconImage(Toolkit.getDefaultToolkit().getImage("resources/GUI_images/CarImageTaskBarIcon32x32.png"));

        this.setSize(1000, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public JPanel getMainPane() {
        return mainPanel;
    }
}
