package io.github.davidcapilla.order_management_kata.order;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {

    Order save(Order order);
}
