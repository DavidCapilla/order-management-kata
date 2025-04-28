package io.github.davidcapilla.order_management_kata.product;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockRepository {

    List<Stock> findAll();

    Stock findById(UUID id);
}
