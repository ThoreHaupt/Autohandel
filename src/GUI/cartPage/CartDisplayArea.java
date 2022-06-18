package GUI.cartPage;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import GUI.UIController;
import Model.UserComponentes.Order;
import lib.uiComponents.rigitFreeSpace;

import java.awt.*;

public class CartDisplayArea extends JPanel {

    UIController uiController;
    Dimension preferredSize;
    JPanel backPanel;

    /**
     * @param uiController
     * @param width
     */
    public CartDisplayArea(UIController uiController, Order[] orders, Dimension preferredSize) {
        this.uiController = uiController;
        this.preferredSize = preferredSize;
        setPreferredSize(preferredSize);
        // @temp:
        setBackground(new Color(255, 0, 0));
        this.add(buildScrollableOrderDisplay(orders));
    }

    private JPanel buildScrollableOrderDisplay(Order[] orders) {
        JPanel panel = new JPanel();

        backPanel = buildOrderCollectionPanel(orders, preferredSize);
        /* JScrollPane scrollbar = new JScrollPane(backPanel);
        scrollbar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        panel.add(scrollbar); */
        panel.add(backPanel);
        return panel;
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
    }

    private JPanel buildOrderCollectionPanel(Order[] orders, Dimension dimension) {
        JPanel panel = new JPanel();
        int width = (int) dimension.getWidth();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        System.out.println(orders.length);
        for (int i = 0; i < orders.length; i++) {
            System.out.println("adding Shit to the Display ig");
            JButton button = new JButton();
            CartEntry entry = new CartEntry(uiController, orders[i], width);
            panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(
                    width, 1)),
                    BorderLayout.NORTH);
            c.gridy++;
            button.add(entry, BorderLayout.CENTER);
            panel.add(button, c);
            c.gridy++;
            panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(
                    width, 1)),
                    BorderLayout.SOUTH);
            c.gridy++;
        }
        return panel;
    }

}
