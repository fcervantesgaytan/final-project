package com.deloitte.springboot.exceptions;

/**
 * Represents a Custom Conflict Exception for the User entity.
 */
public class UserConflictException extends RuntimeException {
    /**
     * Constructor.
     * 
     * @param message Custom message for the exception.
     */
    public UserConflictException(String message) {
        super(message);
    }
}
