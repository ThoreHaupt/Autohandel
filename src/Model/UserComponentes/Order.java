package Model.UserComponentes;

import org.junit.internal.requests.OrderingRequest;

import lib.Event.ChangeToCartEvent;
import lib.technicalComponents.Product;

public class Order {
    private Cart cart;
    private double amount;
    Product product;

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
}
