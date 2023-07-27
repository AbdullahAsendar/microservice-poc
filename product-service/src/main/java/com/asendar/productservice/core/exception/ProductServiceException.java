package com.asendar.productservice.core.exception;

/**
 * @author asendar
 */
public class ProductServiceException extends RuntimeException {

    public ProductServiceException(String message) {
        super(message);
    }

    public ProductServiceException(String message, Object... args) {
        super(String.format(message, args));
    }
}
