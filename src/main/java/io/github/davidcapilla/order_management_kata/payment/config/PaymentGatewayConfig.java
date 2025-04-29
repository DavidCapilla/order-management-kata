package io.github.davidcapilla.order_management_kata.payment.config;

import io.github.davidcapilla.order_management_kata.payment.gateway.MockPaymentGateway;
import io.github.davidcapilla.order_management_kata.payment.gateway.PaymentGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentGatewayConfig {

    @Bean
    public PaymentGateway paymentGateway() {
        return new MockPaymentGateway();
    }
}
