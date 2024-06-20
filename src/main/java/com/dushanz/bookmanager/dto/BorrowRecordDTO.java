package com.dushanz.bookmanager.dto;

import com.dushanz.bookmanager.utils.DateUtils;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowRecordDTO {
    @NotNull(message = "borrowed bookId is mandatory")
    private Integer bookId;
    @NotNull(message = "borrower id is mandatory")
    private Integer borrowerId;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;

    public String getBorrowDate() {
        return DateUtils.formatLocalDateTime(borrowDate);
    }

    public String getReturnDate() {
        return DateUtils.formatLocalDateTime(returnDate);
    }

}
