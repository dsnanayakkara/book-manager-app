package com.dushanz.bookmanager.controller;

import com.dushanz.bookmanager.dto.BorrowRecordDTO;
import com.dushanz.bookmanager.service.BorrowRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/books")
public class BookBorrowController {
    private final BorrowRecordService borrowRecordService;

    @Autowired
    public BookBorrowController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }

    @PostMapping("/{id}/borrow")
    public ResponseEntity<BorrowRecordDTO> borrowBook( @PathVariable Integer id,  @Valid @RequestBody BorrowRecordDTO transaction) {
        transaction.setBookId(id);
        return new ResponseEntity<>(borrowRecordService.borrowBook(id, transaction), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<BorrowRecordDTO> returnBook( @PathVariable Integer id,
                                                       @Valid @RequestBody BorrowRecordDTO transaction) {
        transaction.setBookId(id);
        return new ResponseEntity<>(borrowRecordService.returnBook(id, transaction), HttpStatus.CREATED);
    }
}

