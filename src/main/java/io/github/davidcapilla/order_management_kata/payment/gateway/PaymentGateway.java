package io.github.davidcapilla.order_management_kata.payment.gateway;

import io.github.davidcapilla.order_management_kata.payment.model.PaymentStatus;

public interface PaymentGateway {

    PaymentStatus processPayment(String cardToken, double amount);
}
