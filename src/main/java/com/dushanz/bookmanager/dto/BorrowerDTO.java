package com.dushanz.bookmanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class BorrowerDTO {
    private int id;
    @NotNull(message = "Borrower name is mandatory")
    private String name;
    @Email(message = "Email should be valid")
    private String email;
}
