package io.github.davidcapilla.order_management_kata.payment;

import io.github.davidcapilla.order_management_kata.payment.gateway.PaymentGateway;
import io.github.davidcapilla.order_management_kata.payment.gateway.PaymentStatus;
import io.github.davidcapilla.order_management_kata.product.Price;
import java.time.LocalDateTime;

public record PaymentDetails(Price totalPrice,
                             String cardToken,
                             PaymentStatus paymentStatus,
                             LocalDateTime paymentDate,
                             PaymentGateway paymentGateway) {

}
