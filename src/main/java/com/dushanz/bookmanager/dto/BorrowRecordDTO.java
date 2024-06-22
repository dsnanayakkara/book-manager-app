package com.dushanz.bookmanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowRecordDTO {
    @NotNull(message = "borrowed bookId is mandatory")
    private Integer bookId;
    @NotNull(message = "borrower id is mandatory")
    private Integer borrowerId;
    private String borrowDate;
    private String returnDate;


    @Override
    public String toString() {
        return "BorrowRecordDTO{" +
                "bookId=" + bookId +
                ", borrowerId=" + borrowerId +
                '}';
    }
}
