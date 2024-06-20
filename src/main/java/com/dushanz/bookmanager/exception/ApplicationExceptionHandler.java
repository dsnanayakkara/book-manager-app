package com.dushanz.bookmanager.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleExceptionBadRequest(IllegalArgumentException ex) {
        LOG.error("Bad request, check the input details again {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse("Bad request, check the input details again {}",
                ex.getMessage(), HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleExceptionNotFound(ResourceNotFoundException ex) {
        LOG.error("Requested resource not found on server {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse("Requested resource not found on server {}",
                ex.getMessage(), HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        LOG.error("System Error occurred, please contact the administrator {}", ex.getMessage(), ex);
        return new ResponseEntity<>("System Error occurred, please contact the administrator", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

