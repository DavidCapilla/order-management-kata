package io.github.davidcapilla.order_management_kata.order.controller;

import io.github.davidcapilla.order_management_kata.customer.Seat;
import io.github.davidcapilla.order_management_kata.order.model.Order;
import io.github.davidcapilla.order_management_kata.order.model.OrderStatus;
import io.github.davidcapilla.order_management_kata.order.service.OrderService;
import io.github.davidcapilla.order_management_kata.payment.model.PaymentDetails;
import io.github.davidcapilla.order_management_kata.payment.service.PaymentService;
import io.github.davidcapilla.order_management_kata.payment.model.PaymentStatus;
import io.github.davidcapilla.order_management_kata.product.ProductService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orders")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;
    private ProductService productService;
    private PaymentService paymentService;

    @PostMapping
    public Order createOrder(@RequestBody Seat seat) {
        return orderService.createOrder(seat);
    }

    @PostMapping("/{orderId}/cancel")
    public Order cancelOrder(@PathVariable UUID orderId) {
        return orderService.cancelOrder(orderId);
    }

    @PutMapping
    public Order updateOrder(@RequestBody Order order) {
        if (!productService.hasStock(order.products())) {
            throw new IllegalArgumentException("Some products are out of stock");
        }
        return orderService.updateOrder(order);
    }

    @PostMapping("/{orderId}/process")
    public Order processOrder(@PathVariable UUID orderId) {
        Order order = orderService.getOrder(orderId);
        if (!productService.hasStock(order.products())) {
            throw new IllegalArgumentException("Some products are out of stock");
        }
        if (order.status() != OrderStatus.OPEN) {
            throw new IllegalArgumentException("Order with id " + orderId + " is not open");
        }
        PaymentDetails paymentDetails = paymentService.processPayment(order.paymentDetails());
        if (!PaymentStatus.PAYMENT_FAILED.equals(paymentDetails.paymentStatus())) {
            productService.removeFromStock(order.products());
        }
        return orderService.processOrder(orderId, paymentDetails);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
