package io.github.davidcapilla.order_management_kata.order;

import io.github.davidcapilla.order_management_kata.customer.CustomerDetails;
import io.github.davidcapilla.order_management_kata.product.Product;
import io.github.davidcapilla.order_management_kata.payment.PaymentDetails;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record Order(UUID id,
                    List<Product> products,
                    PaymentDetails paymentDetails,
                    OrderStatus status,
                    CustomerDetails customerDetails) {

}
