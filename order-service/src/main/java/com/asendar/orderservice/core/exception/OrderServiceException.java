package com.asendar.orderservice.core.exception;

/**
 * @author asendar
 */
public class OrderServiceException extends RuntimeException {

    public OrderServiceException(String message) {
        super(message);
    }

    public OrderServiceException(String message, Object... args) {
        super(String.format(message, args));
    }
}
