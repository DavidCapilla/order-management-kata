package io.github.davidcapilla.order_management_kata.order;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.davidcapilla.order_management_kata.customer.Seat;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class OrderServiceTest {

    OrderService orderService = new OrderService();

    @Test
    void createOrderRequiresSeat() {
        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(null));
    }

    @Test
    void createOrderReturnsOrder() {
        Order order = orderService.createOrder(new Seat("E", "1"));
        assertThat(order, is(notNullValue()));
        assertThat(order.products(), is(Collections.emptyList()));
        assertThat(order.status(), is(OrderStatus.OPEN));
        assertThat(order.customerDetails(), is(notNullValue()));
        assertThat(order.customerDetails().seat(), is(notNullValue()));
        assertThat(order.customerDetails().seat().letter(), is("E"));
        assertThat(order.customerDetails().seat().number(), is("1"));
    }
}
