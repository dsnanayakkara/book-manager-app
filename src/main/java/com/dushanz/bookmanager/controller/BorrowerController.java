package com.dushanz.bookmanager.controller;

import com.dushanz.bookmanager.dto.BorrowerDTO;
import com.dushanz.bookmanager.service.BorrowerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/borrower")
public class BorrowerController {
    private final BorrowerService borrowerService;

    @Autowired
    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @PostMapping("")
    public ResponseEntity<BorrowerDTO> registerBorrower(@Valid @RequestBody BorrowerDTO borrower) {
        return new ResponseEntity<>(borrowerService.registerBorrower(borrower), HttpStatus.CREATED);
    }
}
