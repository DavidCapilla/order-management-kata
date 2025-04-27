package io.github.davidcapilla.order_management_kata.order;

import static java.util.Objects.isNull;

import io.github.davidcapilla.order_management_kata.customer.Seat;
import io.github.davidcapilla.order_management_kata.product.Product;
import java.util.List;

public class OrderService {

    public Order createOrder(Seat seat) {
        assertExistingSeat(seat);
        return null;
    }

    public Order createOrder(List<Product> products, Seat seat) {
        assertExistingSeat(seat);
        return null;
    }

    private void assertExistingSeat(Seat seat) {
        if (isNull(seat)) {
            throw new IllegalArgumentException("Seat cannot be null");
        }
    }
}
