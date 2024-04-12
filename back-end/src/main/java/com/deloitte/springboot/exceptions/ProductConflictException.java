package com.deloitte.springboot.exceptions;

/**
 * Represents a Custom Conflict Exception for the Product entity.
 */
public class ProductConflictException extends RuntimeException {
    /**
     * Constructor.
     * 
     * @param message Custom message for the exception.
     */
    public ProductConflictException(String message) {
        super(message);
    }
}
