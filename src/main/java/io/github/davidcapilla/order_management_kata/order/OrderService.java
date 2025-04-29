package io.github.davidcapilla.order_management_kata.order;

import static java.util.Objects.isNull;

import io.github.davidcapilla.order_management_kata.customer.CustomerDetails;
import io.github.davidcapilla.order_management_kata.customer.Seat;
import io.github.davidcapilla.order_management_kata.payment.model.PaymentDetails;
import io.github.davidcapilla.order_management_kata.payment.model.PaymentStatus;
import io.github.davidcapilla.order_management_kata.product.Price;
import io.github.davidcapilla.order_management_kata.product.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
                                            .paymentDetails(new PaymentDetails(
                                                    new Price(0.0),
                                                    "",
                                                    null,
                                                    null,
                                                    null))
                                            .status(OrderStatus.OPEN)
                                            .customerDetails(new CustomerDetails(null, seat))
                                            .build());
    }

    public Order getOrder(UUID orderId) {

        Order order = orderRepository.findById(orderId);
        if (isNull(order)) {
            throw new IllegalArgumentException("Order with id " + orderId + " not found");
        }
        return order;
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

    public Order updateOrder(Order proposedOrder) {

        UUID orderId = proposedOrder.id();
        if (isNull(orderId)) {
            throw new IllegalArgumentException("Order id cannot be null");
        }
        Order storedOrder = orderRepository.findById(orderId);
        if (isNull(storedOrder)) {
            throw new IllegalArgumentException("Order not registered");
        }
        if (storedOrder.status() != OrderStatus.OPEN) {
            throw new IllegalArgumentException("Order with id " + orderId + " is not open");
        }

        List<Product> updatedProducts = updateProducts(proposedOrder);
        PaymentDetails updatedPaymentDetails = updatePaymentDetails(proposedOrder, storedOrder,
                                                                        updatedProducts);
        CustomerDetails updatedCustomerDetails = updateCustomerDetails(proposedOrder, storedOrder);
        return orderRepository.save(Order.builder()
                                            .id(storedOrder.id())
                                            .products(updatedProducts)
                                            .paymentDetails(updatedPaymentDetails)
                                            .status(storedOrder.status())
                                            .customerDetails(updatedCustomerDetails)
                                            .build());
    }

    public Order processOrder(UUID orderId, PaymentDetails paymentDetails) {

        Order order = orderRepository.findById(orderId);
        if (isNull(order)) {
            throw new IllegalArgumentException("Order with id " + orderId + " not found");
        }
        if (order.status() != OrderStatus.OPEN) {
            throw new IllegalArgumentException("Order with id " + orderId + " is not open");
        }

        OrderStatus orderStatus =
                PaymentStatus.PAYMENT_FAILED.equals(paymentDetails.paymentStatus()) ?
                        OrderStatus.OPEN :
                        OrderStatus.FINISHED;
        Order processedOrder = Order.builder()
                .id(order.id())
                .products(order.products())
                .paymentDetails(paymentDetails)
                .status(orderStatus)
                .customerDetails(order.customerDetails())
                .build();
        return orderRepository.save(processedOrder);
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

    private CustomerDetails updateCustomerDetails(Order proposedOrder, Order storedOrder) {

        return new CustomerDetails(proposedOrder.customerDetails().email(),
                                   storedOrder.customerDetails().seat());
    }

    private List<Product> updateProducts(Order proposedOrder) {
        return new ArrayList<>(proposedOrder.products());
    }

    private PaymentDetails updatePaymentDetails(
            Order proposedOrder, Order storedOrder, List<Product> products) {

        double totalPrice = products.stream().mapToDouble(product -> product.price().amount()).sum();
        return new PaymentDetails(
                new Price(totalPrice),
                proposedOrder.paymentDetails().cardToken(),
                storedOrder.paymentDetails().paymentStatus(),
                storedOrder.paymentDetails().paymentDate(),
                storedOrder.paymentDetails().paymentGateway()
        );
    }
}
