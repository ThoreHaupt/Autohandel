package GUI.userPage.cartPage;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import Controller.Controller;
import GUI.UIController;
import Model.UserComponentes.Cart;
import Model.UserComponentes.Order;
import lib.uiComponents.MLLabel;
import lib.uiComponents.MLsortSelector;
import lib.uiComponents.rigitFreeSpace;

import java.awt.*;

public class CartDisplayArea extends JPanel {

    UIController uiController;
    Controller controller;
    Dimension preferredSize;
    JScrollPane scrollPane;
    MLsortSelector sortSelector;
    JPanel backPanel;

    /**
     * @param uiController
     * @param width
     */
    public CartDisplayArea(UIController uiController, Cart cart, Dimension preferredSize) {
        this.uiController = uiController;
        this.preferredSize = preferredSize;
        this.controller = uiController.getController();
        // setPreferredSize(preferredSize);
        // @temp:
        // setBackground(new Color(255, 0, 0));
        updateCart(cart.getOrders());
        controller.addChangeToCartListener(e -> updateCart(cart.getOrders()));
        controller.addPurchaseEventListener(e -> updateCart(cart.getOrders()));
        //uiController.getWindow().addWindowSizeChangeListener(e -> updateCart(orders));
    }

    private void updateCart(Order[] orders) {
        backPanel = buildScrollableOrderDisplay(orders);
        removeAll();
        add(backPanel);
        revalidate();
        repaint();
    }

    private JPanel buildScrollableOrderDisplay(Order[] orders) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        backPanel = buildOrderCollectionPanel(orders, preferredSize);
        JScrollPane scrollbar = new JScrollPane(backPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollbar.setPreferredSize(preferredSize);
        scrollbar.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollbar, BorderLayout.CENTER);
        return panel;
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        this.preferredSize = preferredSize;
        super.setPreferredSize(preferredSize);
        revalidate();
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

        if (orders.length == 0) {
            MLLabel noItemsInCartLabel = new MLLabel(uiController,
                    "There are currently no Items in your cart. Head over to the shop to add some!");
            noItemsInCartLabel.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 20));
            c.fill = GridBagConstraints.BOTH;
            panel.add(noItemsInCartLabel, c);

        } else {

            panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(
                    width, 1)), c);
            c.gridy++;

            sortSelector = new MLsortSelector(uiController);

            panel.add(sortSelector, c);

            c.gridy++;

            panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(
                    width, 5)), c);
            c.gridy++;
        }
        for (int i = 0; i < orders.length; i++) {
            System.out.println("adding Shit to the Display ig");
            JButton entry = new CartEntry(uiController, orders[i], width);
            panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(
                    width, 1)), c);
            c.gridy++;
            panel.add(entry, c);
            c.gridy++;
            panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(
                    width, 1)), c);
            c.gridy++;
        }
        panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(
                width, (int) dimension.getHeight() - orders.length * (CartEntry.height + 2) - 5)), c);

        return panel;
    }

}
