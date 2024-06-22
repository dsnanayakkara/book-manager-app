package com.dushanz.bookmanager.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String LOG_PARAM = "{} {}";
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @Value("${error.badRequest}")
    private String badRequestMessage;

    @Value("${error.notFound}")
    private String resourceNotFoundMessage;

    @Value("${error.systemError}")
    private String systemErrorMessage;

    @Value("${error.authenticationFailed}")
    private String authenticationFailedMessage;

    @Value("${error.accessDenied}")
    private String accessDeniedMessage;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleExceptionBadRequest(IllegalArgumentException ex) {
        LOG.error(LOG_PARAM, badRequestMessage, ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(badRequestMessage,
                ex.getMessage(), HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleExceptionNotFound(ResourceNotFoundException ex) {
        LOG.error(LOG_PARAM, resourceNotFoundMessage, ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(resourceNotFoundMessage,
                ex.getMessage(), HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        LOG.error(LOG_PARAM, systemErrorMessage, ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                systemErrorMessage,
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        LOG.error(LOG_PARAM, authenticationFailedMessage, ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                authenticationFailedMessage, ex.getMessage(), HttpStatus.UNAUTHORIZED.toString()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAuthorizationException(AccessDeniedException ex) {
        LOG.error(LOG_PARAM, accessDeniedMessage, ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                accessDeniedMessage, ex.getMessage(), HttpStatus.FORBIDDEN.toString()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

}
