package io.github.davidcapilla.order_management_kata.product.repository;

import io.github.davidcapilla.order_management_kata.product.model.Stock;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockRepository {

    Stock save(Stock stock);

    List<Stock> findAll();

    Stock findById(UUID id);
}
