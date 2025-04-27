package io.github.davidcapilla.order_management_kata.order;

import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {

    Order save(Order order);

    Order findById(UUID id);
}
