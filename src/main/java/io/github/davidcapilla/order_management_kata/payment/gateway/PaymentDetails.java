package io.github.davidcapilla.order_management_kata.payment.gateway;

import io.github.davidcapilla.order_management_kata.product.Price;
import java.time.LocalDateTime;

public record PaymentDetails(Price totalPrice,
                             String cardToken,
                             PaymentStatus paymentStatus,
                             LocalDateTime paymentDate,
                             PaymentGateway paymentGateway) {

}
