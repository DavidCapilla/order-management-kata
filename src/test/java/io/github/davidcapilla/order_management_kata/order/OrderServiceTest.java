package io.github.davidcapilla.order_management_kata.order;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.github.davidcapilla.order_management_kata.customer.Seat;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @Test
    void createOrderRequiresSeat() {
        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(null));
    }

    @Test
    void createOrderReturnsOrder() {

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

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
