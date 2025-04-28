package io.github.davidcapilla.order_management_kata.order;


import io.github.davidcapilla.order_management_kata.customer.Seat;
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}