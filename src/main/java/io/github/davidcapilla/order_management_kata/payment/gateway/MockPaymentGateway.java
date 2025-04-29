package io.github.davidcapilla.order_management_kata.payment.gateway;

import io.github.davidcapilla.order_management_kata.payment.PaymentStatus;

public class MockPaymentGateway implements PaymentGateway {

    @Override
    public PaymentStatus processPayment(String cardToken, double amount) {
        return PaymentStatus.PAID;
    }
}
