package io.github.davidcapilla.order_management_kata.model;

import java.util.List;

public record Order(List<Product> products,
                    PaymentDetails paymentDetails,
                    OrderStatus status,
                    CustomerDetails customerDetails) {

}
