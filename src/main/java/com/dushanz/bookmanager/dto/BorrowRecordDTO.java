package com.dushanz.bookmanager.dto;

import com.dushanz.bookmanager.utils.DateUtils;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowRecordDTO {
    @NotBlank(message = "borrowed bookId is mandatory")
    private int bookId;
    @NotBlank(message = "borrower id is mandatory")
    private int borrowerId;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;

    public String getBorrowDate() {
        return DateUtils.formatLocalDateTime(borrowDate);
    }

    public String getReturnDate() {
        return DateUtils.formatLocalDateTime(returnDate);
    }

}
