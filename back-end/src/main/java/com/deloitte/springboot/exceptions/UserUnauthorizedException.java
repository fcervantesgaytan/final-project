package com.deloitte.springboot.exceptions;

public class UserUnauthorizedException extends RuntimeException {
    /**
     * Constructor.
     * 
     * @param message Custom message for the exception.
     */
    public UserUnauthorizedException(String message) {
        super(message);
    }
}
