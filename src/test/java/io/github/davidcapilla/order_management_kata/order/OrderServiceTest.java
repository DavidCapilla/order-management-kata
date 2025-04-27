package io.github.davidcapilla.order_management_kata.order;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.davidcapilla.order_management_kata.product.Product;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class OrderServiceTest {

    OrderService orderService = new OrderService();

    @Test
    void createOrderRequiresSeat() {
        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(null));
    }

    @Test
    void createOrderWithProductsRequiresSeat() {
        List<Product> products = Collections.emptyList();
        assertThrows(IllegalArgumentException.class,
                     () -> orderService.createOrder(products, null));
    }

}