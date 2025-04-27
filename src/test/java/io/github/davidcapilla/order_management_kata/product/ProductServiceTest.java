package io.github.davidcapilla.order_management_kata.product;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductStockRepository productStockRepository;

    @InjectMocks
    ProductService productService;

    @Test
    void getProduct_whenProductNotFound_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> productService.getProduct(null));
    }

    @Test
    void getProduct_whenProductFound_returnsProduct() {

        Product testProduct = new Product(
                UUID.randomUUID(),
                "Test Product",
                new Price(100.0),
                new Category(
                        UUID.randomUUID(),
                        "Test Category"),
                new Image("url/to/image.jpg"));

        Stock stock = new Stock(testProduct, 10);
        when(productStockRepository.findById(testProduct.id())).thenReturn(stock);

        Product result = productService.getProduct(testProduct.id());

        assertThat(result, is(notNullValue()));
        assertThat(result.id(), is(testProduct.id()));
        assertThat(result.name(), is(testProduct.name()));
        assertThat(result.price(), is(testProduct.price()));
        assertThat(result.category(), is(testProduct.category()));
        assertThat(result.image(), is(testProduct.image()));
    }
}
