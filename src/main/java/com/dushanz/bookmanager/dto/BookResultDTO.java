package com.dushanz.bookmanager.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookResultDTO {
    private List<BookDTO> booksList;
    private int totalBooks;
    private int availableBooks;
}
