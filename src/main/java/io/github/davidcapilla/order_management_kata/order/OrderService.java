package io.github.davidcapilla.order_management_kata.order;

import static java.util.Objects.isNull;

import io.github.davidcapilla.order_management_kata.customer.CustomerDetails;
import io.github.davidcapilla.order_management_kata.customer.Seat;
import java.util.Collections;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public Order createOrder(Seat seat) {
        assertExistingSeat(seat);
        return Order.builder()
                .products(Collections.emptyList())
                .status(OrderStatus.OPEN)
                .customerDetails(new CustomerDetails(null, seat))
                .build();
    }

    private void assertExistingSeat(Seat seat) {
        if (isNull(seat)) {
            throw new IllegalArgumentException("Seat cannot be null");
        }
        if (isNull(seat.letter()) || isNull(seat.number())) {
            throw new IllegalArgumentException("Seat letter and number cannot be null");
        }
        if (seat.letter().isEmpty() || seat.number().isEmpty()) {
            throw new IllegalArgumentException("Seat letter and number cannot be empty");
        }
    }
}
