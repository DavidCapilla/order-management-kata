package io.github.davidcapilla.order_management_kata.product;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
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

    public boolean hasStock(List<Product> products) {

        Map<UUID, Long> productQuantities = products.stream()
                .map(Product::id)
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        for (Map.Entry<UUID, Long> entry : productQuantities.entrySet()) {
            UUID productId = entry.getKey();
            long quantity = entry.getValue();

            Stock stock = productStockRepository.findById(productId);
            if (isNull(stock)) {
                throw new IllegalArgumentException("Product with id " + productId + " not found");
            }

            if (stock.quantity() < quantity) {
                return false;
            }
        }
        return true;
    }

    public void removeFromStock(List<Product> products) {

        Map<UUID, Long> productQuantities = products.stream()
                .map(Product::id)
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        for (Map.Entry<UUID, Long> entry : productQuantities.entrySet()) {
            UUID productId = entry.getKey();
            long quantity = entry.getValue();

            Stock stock = productStockRepository.findById(productId);
            if (isNull(stock)) {
                throw new IllegalArgumentException("Product with id " + productId + " not found");
            }

            if (stock.quantity() < quantity) {
                throw new IllegalArgumentException("Not enough stock for product with id " + productId);
            }

            productStockRepository.save(new Stock(stock.product(),
                                                  (int) (stock.quantity() - quantity)));
        }
    }
}
