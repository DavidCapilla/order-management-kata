package io.github.davidcapilla.order_management_kata.product;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

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
                        "Test Category",
                        null),
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

    @Test
    void getProducts_returnsAllProducts() {

        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        Product product3 = mock(Product.class);

        List<Stock> stock = List.of(
                new Stock(product1, 10),
                new Stock(product2, 20),
                new Stock(product3, 30));

        when(productStockRepository.findAll()).thenReturn(stock);

        List<Product> result = productService.getProducts();
        assertThat(result, is(notNullValue()));
        assertThat(result, containsInAnyOrder(product1, product2, product3)
        );
    }

    @Test
    void hasStock_whenProductNotFound_throwsIllegalArgumentException() {
        List<Product> products = List.of(new Product(
                UUID.randomUUID(),
                "Test Product",
                new Price(100.0),
                new Category(
                        UUID.randomUUID(),
                        "Test Category",
                        null),
                new Image("url/to/image.jpg")));
        assertThrows(IllegalArgumentException.class,
                     () -> productService.hasStock(products));
    }

    @Test
//    use 'lenient' strictness
    @MockitoSettings(strictness = Strictness.LENIENT)
    void hasStock_whenNotEnoughStock_thenReturnFalse() {

        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        Product product3 = mock(Product.class);
        UUID product1Id = UUID.randomUUID();
        UUID product2Id = UUID.randomUUID();
        UUID product3Id = UUID.randomUUID();
        when(product1.id()).thenReturn(product1Id);
        when(product2.id()).thenReturn(product2Id);
        when(product3.id()).thenReturn(product3Id);

        Stock stock1 = new Stock(product1, 2);
        Stock stock2 = new Stock(product2, 3);
        Stock stock3 = new Stock(product3, 1);

        when(productStockRepository.findById(product1Id)).thenReturn(stock1);
        when(productStockRepository.findById(product2Id)).thenReturn(stock2);
        when(productStockRepository.findById(product3Id)).thenReturn(stock3);

        boolean result = productService.hasStock(
                List.of(
                        product1,
                        product2,
                        product3,
                        product1,
                        product3));

        assertThat(result, is(false));
    }

    @Test
    void hasStock_whenEnoughStock_thenReturnTrue() {

        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        Product product3 = mock(Product.class);
        UUID product1Id = UUID.randomUUID();
        UUID product2Id = UUID.randomUUID();
        UUID product3Id = UUID.randomUUID();
        when(product1.id()).thenReturn(product1Id);
        when(product2.id()).thenReturn(product2Id);
        when(product3.id()).thenReturn(product3Id);

        Stock stock1 = new Stock(product1, 2);
        Stock stock2 = new Stock(product2, 3);
        Stock stock3 = new Stock(product3, 5);

        when(productStockRepository.findById(product1Id)).thenReturn(stock1);
        when(productStockRepository.findById(product2Id)).thenReturn(stock2);
        when(productStockRepository.findById(product3Id)).thenReturn(stock3);

        boolean result = productService.hasStock(
                List.of(
                        product1,
                        product2,
                        product3,
                        product1,
                        product3));

        assertThat(result, is(true));
    }
}
