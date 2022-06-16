package Model.UserComponentes;

import javax.swing.event.EventListenerList;

import lib.DataStructures.HashMapImplementation.THashMap;
import lib.Event.ChangeToCartEvent;
import lib.Event.ChangeToCartListener;
import lib.Other.SupportingCalculations;

public class Cart {
    THashMap<Integer, Order> contents;
    User user;

    protected EventListenerList listenerList = new EventListenerList();

    public Cart() {

    }

    public void addToCart(Order p) {
        contents.put(p.getID(), p);
        fireAddToCartEvent(new ChangeToCartEvent(this));
    }

    public void removeFromCart(Order p) {
        fireAddToCartEvent(new ChangeToCartEvent(this));
    }

    protected void fireAddToCartEvent(ChangeToCartEvent event) {
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

}
