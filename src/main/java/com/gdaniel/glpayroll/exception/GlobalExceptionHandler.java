package com.gdaniel.glpayroll.exception;

import java.time.LocalDateTime;

//import org.hibernate.validator.internal.util.logging.LoggerFactory;
import ch.qos.logback.classic.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // private static final Logger logger =
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle specific custom exception (e.g., ResourceNotFound)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        LOGGER.error("Error Details: {}", error);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleBadRequest(BadRequestException ex,
            WebRequest request) {
        ErrorDetails error = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        LOGGER.error("Error Details: {}", error);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDetails> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        LOGGER.error("Error Details: {}", error);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // Fallback for all other unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex,
            WebRequest request) {
        ErrorDetails error = new ErrorDetails(LocalDateTime.now(), "An internal error occurred",
                request.getDescription(false));

        LOGGER.error("Error Details: {}", error, ex);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorDetails> handleFileUploadException(FileUploadException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        LOGGER.error("Error Details: {}", error);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
