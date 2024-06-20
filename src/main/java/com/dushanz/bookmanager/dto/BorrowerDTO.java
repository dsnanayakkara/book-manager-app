package com.dushanz.bookmanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class BorrowerDTO {
    private int id;
    @NotBlank(message = "Borrower name is mandatory")
    private String name;
    @Email(message = "Email should be valid")
    private String email;
}
