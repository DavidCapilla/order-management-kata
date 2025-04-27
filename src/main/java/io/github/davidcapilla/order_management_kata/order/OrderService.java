package io.github.davidcapilla.order_management_kata.order;

import static java.util.Objects.isNull;

import io.github.davidcapilla.order_management_kata.customer.CustomerDetails;
import io.github.davidcapilla.order_management_kata.customer.Seat;
import java.util.Collections;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Seat seat) {

        assertExistingSeat(seat);
        return orderRepository.save(Order.builder()
                                            .id(UUID.randomUUID())
                                            .products(Collections.emptyList())
                                            .status(OrderStatus.OPEN)
                                            .customerDetails(new CustomerDetails(null, seat))
                                            .build());
    }

    public Order cancelOrder(UUID orderId) {

        Order order = orderRepository.findById(orderId);
        if (isNull(order)) {
            throw new IllegalArgumentException("Order with id " + orderId + " not found");
        }
        if (order.status() != OrderStatus.OPEN) {
            throw new IllegalArgumentException("Order with id " + orderId + " is not open");
        }
        Order cancelledOrder = Order.builder()
                .id(order.id())
                .products(order.products())
                .status(OrderStatus.DROPPED)
                .customerDetails(order.customerDetails())
                .build();
        return orderRepository.save(cancelledOrder);
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
