package com.dushanz.bookmanager.dto;

import com.dushanz.bookmanager.utils.DateUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowRecordDTO {
    private int bookId;
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
