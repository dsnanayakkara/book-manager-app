package com.dushanz.bookmanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookDTO {
    @NotNull(message = "Book id is mandatory")
    private int id;
    @NotNull(message = "Book title is mandatory")
    private String title;
    @NotNull(message = "Book Author name is mandatory")
    private String author;
    @NotNull(message = "Book isbn name is mandatory")
    private String isbn;
}
