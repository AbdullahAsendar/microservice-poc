package com.asendar.core.command;

import com.asendar.core.model.PaymentDetails;
import lombok.Builder;
import org.axonframework.modelling.command.AggregateIdentifier;

/**
 * @author asendar
 */
@Builder
public record ProcessPaymentCommand(@AggregateIdentifier String paymentId, String orderId, PaymentDetails paymentDetails) {
}
