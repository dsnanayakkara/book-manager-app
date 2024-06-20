package com.dushanz.bookmanager.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookResultDTO {
    private List<BookDTO> result;
    private int totalBooks;
}
