package io.github.davidcapilla.order_management_kata.payment.gateway;

import io.github.davidcapilla.order_management_kata.payment.model.PaymentStatus;
import lombok.Getter;

@Getter
public class MockPaymentGateway implements PaymentGateway {

    private final String provider = "MOCK";

    @Override
    public PaymentStatus processPayment(String cardToken, double amount) {
        return PaymentStatus.PAID;
    }
}
