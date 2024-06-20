package com.dushanz.bookmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookDTO {
    private Integer id;
    @NotBlank(message = "Book title is mandatory")
    private String title;
    @NotBlank(message = "Book Author name is mandatory")
    private String author;
    @NotBlank(message = "Book isbn name is mandatory")
    private String isbn;
}
