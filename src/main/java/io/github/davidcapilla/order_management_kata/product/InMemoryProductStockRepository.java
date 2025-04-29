package io.github.davidcapilla.order_management_kata.product;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryProductStockRepository implements ProductStockRepository {

    private final ConcurrentMap<UUID, Stock> stock = InitialStock.generateStock();

    @Override
    public Stock save(Stock theStock) {
        stock.put(theStock.product().id(), theStock);
        return theStock;
    }

    @Override
    public List<Stock> findAll() {
        return stock.values().stream().toList();
    }

    @Override
    public Stock findById(UUID id) {
        return stock.get(id);
    }
}
