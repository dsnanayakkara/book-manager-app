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
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("")
    public ResponseEntity<BookResultDTO> getBooks(
            @RequestParam(value = "status", required = false) Boolean status,
            @RequestParam(value = "unique", defaultValue = "false") boolean unique) {

        BookResultDTO result;
        if (Boolean.TRUE.equals(status)) {
            // If status is true, fetch only available books
            result = bookService.getBooksAvailableForBorrow(unique);
        } else {
            // If status is not true (including null), fetch all books
            result = bookService.getAllBooks(unique);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<BookDTO> register(@Valid @RequestBody BookDTO book) {
        BookDTO savedBook = bookService.registerNewBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

}
