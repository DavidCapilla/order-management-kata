package io.github.davidcapilla.order_management_kata.order.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.davidcapilla.order_management_kata.customer.CustomerDetails;
import io.github.davidcapilla.order_management_kata.customer.Seat;
import io.github.davidcapilla.order_management_kata.order.model.Order;
import io.github.davidcapilla.order_management_kata.order.model.OrderStatus;
import io.github.davidcapilla.order_management_kata.order.repository.OrderRepository;
import io.github.davidcapilla.order_management_kata.payment.model.PaymentDetails;
import io.github.davidcapilla.order_management_kata.payment.model.PaymentStatus;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
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
        assertThat(order.id(), is(notNullValue()));
        assertThat(order.products(), is(Collections.emptyList()));
        assertThat(order.status(), is(OrderStatus.OPEN));
        assertThat(order.customerDetails(), is(notNullValue()));
        assertThat(order.customerDetails().seat(), is(notNullValue()));
        assertThat(order.customerDetails().seat().letter(), is("E"));
        assertThat(order.customerDetails().seat().number(), is("1"));
    }

    @Test
    void cancelOrder_whenOpenOrder_ReturnsOrderWithDroppedStatus() {

        Order order = Order.builder()
                .id(java.util.UUID.randomUUID())
                .products(Collections.emptyList())
                .status(OrderStatus.OPEN)
                .customerDetails(new CustomerDetails(null, new Seat("E", "1")))
                .build();

        when(orderRepository.findById(order.id())).thenReturn(order);
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order cancelledOrder = orderService.cancelOrder(order.id());
        assertThat(cancelledOrder, is(notNullValue()));
        assertThat(cancelledOrder.id(), is(order.id()));
        assertThat(cancelledOrder.products(), is(order.products()));
        assertThat(cancelledOrder.status(), is(OrderStatus.DROPPED));
        assertThat(cancelledOrder.customerDetails(), is(order.customerDetails()));
        assertThat(cancelledOrder.customerDetails().seat(), is(order.customerDetails().seat()));
        assertThat(cancelledOrder.customerDetails().seat().letter(), is("E"));
        assertThat(cancelledOrder.customerDetails().seat().number(), is("1"));
    }

    @Test
    void cancelOrder_whenOrderNotFound_ThrowsIllegalArgumentException() {

        java.util.UUID orderId = java.util.UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.cancelOrder(orderId));
        assertThat(exception.getMessage(), is("Order with id " + orderId + " not found"));
    }

    @Test
    void cancelOrder_whenOrderNotOpen_ThrowsIllegalArgumentException() {

        Order order = Order.builder()
                .id(java.util.UUID.randomUUID())
                .products(Collections.emptyList())
                .status(OrderStatus.DROPPED)
                .customerDetails(new CustomerDetails(null, new Seat("E", "1")))
                .build();

        UUID orderId = order.id();
        when(orderRepository.findById(orderId)).thenReturn(order);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.cancelOrder(orderId));
        assertThat(exception.getMessage(), is("Order with id " + orderId + " is not open"));
    }

    @Test
    void updateOrder_whenOrderNotFound_ThrowsIllegalArgumentException() {

        Order order = Order.builder()
                .id(UUID.randomUUID())
                .products(Collections.emptyList())
                .status(OrderStatus.OPEN)
                .customerDetails(new CustomerDetails(null, new Seat("E", "1")))
                .build();

        when(orderRepository.findById(order.id())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.updateOrder(order));
        assertThat(exception.getMessage(), is("Order not registered"));
    }

    @Test
    void updateOrder_whenOrderNotOpen_ThrowsIllegalArgumentException() {

        Order order = Order.builder()
                .id(UUID.randomUUID())
                .products(Collections.emptyList())
                .status(OrderStatus.DROPPED)
                .customerDetails(new CustomerDetails(null, new Seat("E", "1")))
                .build();

        when(orderRepository.findById(order.id())).thenReturn(order);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.updateOrder(order));
        assertThat(exception.getMessage(), is("Order with id " + order.id() + " is not open"));
    }

    @Test
    void processOrder_whenOrderNotFound_ThrowsIllegalArgumentException() {

        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.processOrder(orderId, null));
        assertThat(exception.getMessage(), is("Order with id " + orderId + " not found"));
    }

    @Test
    void processOrder_whenOrderNotOpen_ThrowsIllegalArgumentException() {

        Order order = Order.builder()
                .id(UUID.randomUUID())
                .products(Collections.emptyList())
                .status(OrderStatus.DROPPED)
                .customerDetails(new CustomerDetails(null, new Seat("E", "1")))
                .build();

        UUID orderId = order.id();
        when(orderRepository.findById(orderId)).thenReturn(order);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.processOrder(orderId, mock(PaymentDetails.class)));
        assertThat(exception.getMessage(), is("Order with id " + orderId + " is not open"));
    }

    @Test
    void processOrder_whenPaymentFailed_thenOrderStillOpen() {
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .products(Collections.emptyList())
                .status(OrderStatus.OPEN)
                .customerDetails(new CustomerDetails(null, new Seat("E", "1")))
                .build();

        UUID orderId = order.id();
        when(orderRepository.findById(orderId)).thenReturn(order);
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PaymentDetails paymentDetails = mock(PaymentDetails.class);
        when(paymentDetails.paymentStatus()).thenReturn(PaymentStatus.PAYMENT_FAILED);

        Order processedOrder = orderService.processOrder(orderId, paymentDetails);
        assertThat(processedOrder.status(), is(OrderStatus.OPEN));
    }

    @ParameterizedTest
    @EnumSource(value = PaymentStatus.class, names = "PAYMENT_FAILED", mode = EnumSource.Mode.EXCLUDE)
    void processOrder_whenPaymentAccepted_thenFinishOrder(PaymentStatus paymentStatus) {
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .products(Collections.emptyList())
                .status(OrderStatus.OPEN)
                .customerDetails(new CustomerDetails(null, new Seat("E", "1")))
                .build();

        UUID orderId = order.id();
        when(orderRepository.findById(orderId)).thenReturn(order);
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PaymentDetails paymentDetails = mock(PaymentDetails.class);
        when(paymentDetails.paymentStatus()).thenReturn(paymentStatus);

        Order processedOrder = orderService.processOrder(orderId, paymentDetails);
        assertThat(processedOrder.status(), is(OrderStatus.FINISHED));
    }
}
