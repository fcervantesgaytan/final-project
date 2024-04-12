package com.deloitte.springboot.exceptionhandlers;

import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.deloitte.springboot.exceptions.ProductConflictException;
import com.deloitte.springboot.exceptions.UserConflictException;
import com.deloitte.springboot.exceptions.UserUnauthorizedException;

@Service
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Handler for UserUnauthorizedException.
     * 
     * @param exception The exception to handle.
     * @param request Web Request
     * @return A ResponseEntity.
     */
    @ExceptionHandler(value = UserUnauthorizedException.class)
    protected ResponseEntity<Object> handleUnauthorized(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    /**
     * Handler for NoSuchElementException.
     * 
     * @param exception The exception to handle.
     * @param request Web Request
     * @return A ResponseEntity.
     */
    @ExceptionHandler(value = NoSuchElementException.class)
    protected ResponseEntity<Object> handleNoSuchElement(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(exception, "Not Found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handler for Conflic Status Exceptions.
     * If more Conflict Exceptions are created make sure
     * to add them to the value array at the annotation.
     * 
     * @param exception The exception to handle.
     * @param request Web Request
     * @return A ResponseEntity.
     */
    @ExceptionHandler(value = { UserConflictException.class, ProductConflictException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    /**
     * Handler for IllegalArgumentException.
     * This is here as a fallback. This exception
     * should not be thrown from anywhere.
     * 
     * @param exception The exception to handle.
     * @param request Web Request
     * @return A ResponseEntity.
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgument(RuntimeException exception, WebRequest request) {
        return handleNoSuchElement(exception, request);
    }
}
