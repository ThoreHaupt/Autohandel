package Model.UserComponentes;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Model.ModelComponentes.Product;
import lib.DataStructures.HashMapImplementation.THashMap;
import lib.Event.ChangeToCartEvent;
import lib.Event.ChangeToCartListener;
import lib.Other.SupportingCalculations;

public class Cart implements Serializable {
    THashMap<Integer, Order> contents;
    User user;

    protected transient EventListenerList listenerList = new EventListenerList();

    public Cart() {
        contents = new THashMap<>();
    }

    public void addToCart(Order p) {
        contents.put(p.getID(), p);
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

    public void removeFromCart(Order p) {
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

    protected void fireChangeToCartEvent(ChangeToCartEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == ChangeToCartListener.class) {
                ((ChangeToCartListener) listeners[i + 1]).onChangeToCart(event);
            }
        }
    }

    public void addAddToCartListener(ChangeToCartListener listener) {
        listenerList.add(ChangeToCartListener.class, listener);
    }

    public void removeAddToCartListener(ChangeToCartListener listener) {
        listenerList.remove(ChangeToCartListener.class, listener);
    }

    public double getTotalPrice() {
        return SupportingCalculations.round(calculatePrice(), 2);
    }

    /**
     * Sums up the Values of all Orders in the Basket
     * @return the Total value, not rounded
     */
    private double calculatePrice() {
        double sum = 0;
        for (Order order : contents) {
            sum += order.getOrderValue();
        }
        return sum;
    }

    public void deleteOrder(Order order) {
        contents.remove(order.getID());
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

    public Order[] getOrders() {
        ArrayList<Order> tempList = new ArrayList<>();
        contents.forEach(e -> tempList.add(e));

        return tempList.toArray(new Order[contents.size()]);
    }

    public void order(Product product, int amount) {
        Order newOrder = new Order(product, amount);
        contents.put(newOrder.getID(), newOrder);
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

    public void notifyChange() {
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

}
