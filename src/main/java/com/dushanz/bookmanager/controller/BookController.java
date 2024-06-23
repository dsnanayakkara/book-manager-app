package com.dushanz.bookmanager.controller;

import com.dushanz.bookmanager.dto.BookDTO;
import com.dushanz.bookmanager.dto.BookResultDTO;
import com.dushanz.bookmanager.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("")
    public ResponseEntity<BookResultDTO> getBooks(
            @RequestParam(value = "available_only", defaultValue = "false") boolean status,
            @RequestParam(value = "unique_only", defaultValue = "false") boolean unique) {
        // The 'status' parameter is used to filter the books based on their availability.
        // If 'status' is true, only available books are fetched.
        // If 'status' is false, all registered books are fetched, regardless of their availability.
        // The 'unique' parameter is used to decide whether to include multiple copies of the same book in the result.
        // If 'unique' is true, only one copy of each book included in the result.
        BookResultDTO result = status ? bookService.getBooksAvailableForBorrow(unique) : bookService.getAllBooks(unique);

        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<BookDTO> registerBook(@Valid @RequestBody BookDTO book) {
        BookDTO savedBook = bookService.registerNewBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

}
