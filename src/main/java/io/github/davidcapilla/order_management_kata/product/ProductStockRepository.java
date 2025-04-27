package io.github.davidcapilla.order_management_kata.product;

import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockRepository {

    Stock findById(UUID id);
}
