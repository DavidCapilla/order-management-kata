package io.github.davidcapilla.order_management_kata.payment.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import io.github.davidcapilla.order_management_kata.payment.gateway.PaymentGateway;
import io.github.davidcapilla.order_management_kata.payment.model.PaymentDetails;
import io.github.davidcapilla.order_management_kata.payment.model.PaymentStatus;
import io.github.davidcapilla.order_management_kata.product.model.Price;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    private static final LocalDateTime TIME = LocalDateTime.of(2025, 10, 1, 8, 55, 13);

    @Mock
    PaymentGateway paymentGateway;

    @InjectMocks
    PaymentService paymentService;

    private MockedStatic<LocalDateTime> localDateTimeMock;

    @BeforeEach
    void setUpMocks() {
        localDateTimeMock = mockStatic(LocalDateTime.class);
        localDateTimeMock
                .when(LocalDateTime::now)
                .thenReturn(TIME);
    }

    @AfterEach
    void tearDownMocks() {
        localDateTimeMock.close();
    }


    @ParameterizedTest
    @EnumSource(PaymentStatus.class)
    void processPayment(PaymentStatus resultStatus) {

        String cardToken = "cardToken";
        double totalPrice = 100.0;
        PaymentDetails paymentDetails = new PaymentDetails(new Price(totalPrice), cardToken, null, null, null);

        when(paymentGateway.processPayment(cardToken, totalPrice))
                .thenReturn(resultStatus);

        PaymentDetails result = paymentService.processPayment(paymentDetails);

        assertThat(result, is(notNullValue()));
        assertThat(result.totalPrice(), is(paymentDetails.totalPrice()));
        assertThat(result.cardToken(), is(paymentDetails.cardToken()));
        assertThat(result.paymentStatus(), is(resultStatus));
        assertThat(result.paymentDate(), is(TIME));
        assertThat(result.paymentGateway(), is(paymentGateway));
    }
}
