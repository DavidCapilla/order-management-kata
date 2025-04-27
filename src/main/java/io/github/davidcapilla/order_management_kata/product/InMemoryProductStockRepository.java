package io.github.davidcapilla.order_management_kata.product;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryProductStockRepository implements ProductStockRepository {

    private final ConcurrentHashMap<UUID, Stock> stock = new ConcurrentHashMap<>();

    @Override
    public Stock findById(UUID id) {
        return stock.get(id);
    }
}
