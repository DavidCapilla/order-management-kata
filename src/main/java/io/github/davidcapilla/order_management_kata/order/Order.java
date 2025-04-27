package io.github.davidcapilla.order_management_kata.order;

import io.github.davidcapilla.order_management_kata.customer.CustomerDetails;
import io.github.davidcapilla.order_management_kata.product.Product;
import io.github.davidcapilla.order_management_kata.payment.PaymentDetails;
import java.util.List;
import lombok.Builder;

@Builder
public record Order(Long id,
                    List<Product> products,
                    PaymentDetails paymentDetails,
                    OrderStatus status,
                    CustomerDetails customerDetails) {

}
