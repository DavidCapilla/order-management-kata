package io.github.davidcapilla.order_management_kata.payment.service;

import io.github.davidcapilla.order_management_kata.payment.gateway.PaymentGateway;
import io.github.davidcapilla.order_management_kata.payment.model.PaymentDetails;
import io.github.davidcapilla.order_management_kata.payment.model.PaymentStatus;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentGateway paymentGateway;

    public PaymentService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public PaymentDetails processPayment(PaymentDetails paymentDetails) {

        PaymentStatus paymentStatus = paymentGateway.processPayment(
                paymentDetails.cardToken(),
                paymentDetails.totalPrice().amount());

        return new PaymentDetails(paymentDetails.totalPrice(),
                                  paymentDetails.cardToken(),
                                  paymentStatus,
                                  LocalDateTime.now(),
                                  paymentGateway);
    }
}
