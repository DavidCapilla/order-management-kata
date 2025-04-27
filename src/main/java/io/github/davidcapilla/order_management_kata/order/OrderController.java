package io.github.davidcapilla.order_management_kata.order;


import io.github.davidcapilla.order_management_kata.customer.Seat;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping("/create")
    public Order createOrder(@RequestBody Seat seat) {
        return orderService.createOrder(seat);
    }
}