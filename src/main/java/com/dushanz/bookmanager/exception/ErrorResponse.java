package com.dushanz.bookmanager.exception;

import lombok.Data;

/**
 * DTO encapsulating details from exceptions occurred in the system.
 */
@Data
public class ErrorResponse {
    private String message;
    private String details;
    private String status;

    public ErrorResponse(String message, String details, String status) {
        this.message = message;
        this.details = details;
        this.status = status;
    }
}
