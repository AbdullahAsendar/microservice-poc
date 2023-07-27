package com.asendar.userservice.query;

import com.asendar.core.model.PaymentDetails;
import com.asendar.core.model.User;
import com.asendar.core.query.FetchUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

/**
 * @author asendar
 */
@Component
public class UserQueryHandler {

    @QueryHandler
    public User findUserPaymentDetails(FetchUserPaymentDetailsQuery query) {
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .cardNumber("123Card")
                .cvv("123")
                .name("SERGEY KARGOPOLOV")
                .validUntilMonth(12)
                .validUntilYear(2030)
                .build();

        return User.builder()
                .firstName("Sergey")
                .lastName("Kargopolov")
                .userId(query.userId())
                .paymentDetails(paymentDetails)
                .build();
    }

}
