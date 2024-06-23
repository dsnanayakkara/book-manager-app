package com.dushanz.bookmanager.exception;

/**
 * Custom Exception programmatically thrown when a REST endpoint cannot find the requested resource.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }

}

