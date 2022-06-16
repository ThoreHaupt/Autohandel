package Model.UserComponentes;

import lib.Event.ChangeToCartEvent;
import lib.technicalComponents.Product;

public class Order {
    private Cart cart;
    private int amount;
    Product product;
    private int ID;

    public Order(Cart cart, Product product, int amount) {
        this.cart = cart;
        this.product = product;
        this.amount = amount;
    }

    public double getOrderValue() {
        return product.getPrice() * amount;
    }

    public void changeOrder() {
        cart.fireAddToCartEvent(new ChangeToCartEvent(cart));
    }

    public int getID() {
        return ID;
    }
}
