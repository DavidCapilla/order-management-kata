package io.github.davidcapilla.order_management_kata.product;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductStockRepository productStockRepository;

    public List<Product> getProducts() {
        return productStockRepository.findAll().stream()
                .map(Stock::product)
                .toList();
    }

    public Product getProduct(UUID id) {

        Stock stock = productStockRepository.findById(id);
        if (isNull(stock)) {
            throw new IllegalArgumentException("Product with id " + id + " not found");
        }
        return stock.product();
    }
}
