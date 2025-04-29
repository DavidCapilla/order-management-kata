package io.github.davidcapilla.order_management_kata.order.repository;

import io.github.davidcapilla.order_management_kata.order.model.Order;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

    private final ConcurrentHashMap<UUID, Order> orders = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        orders.put(order.id(), order);
        return order;
    }

    @Override
    public Order findById(UUID id) {
        return orders.get(id);
    }
}
