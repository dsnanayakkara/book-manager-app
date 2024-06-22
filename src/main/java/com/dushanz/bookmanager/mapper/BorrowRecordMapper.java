package com.dushanz.bookmanager.mapper;

import com.dushanz.bookmanager.dto.BorrowRecordDTO;
import com.dushanz.bookmanager.entity.Book;
import com.dushanz.bookmanager.entity.BorrowRecord;
import com.dushanz.bookmanager.entity.Borrower;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface BorrowRecordMapper {
    BorrowRecordMapper INSTANCE = Mappers.getMapper(BorrowRecordMapper.class);

    @Mapping(source = "bookId", target = "book")
    @Mapping(source = "borrowerId", target = "borrower")
    @Mapping(source = "borrowDate", target = "borrowDate", qualifiedByName = "parseDateTime")
    @Mapping(source = "returnDate", target = "returnDate", qualifiedByName = "parseDateTime")
    BorrowRecord dtoToEntity(BorrowRecordDTO dto);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "borrower.id", target = "borrowerId")
    @Mapping(source = "borrowDate", target = "borrowDate", qualifiedByName = "formatDateTime")
    @Mapping(source = "returnDate", target = "returnDate", qualifiedByName = "formatDateTime")
    BorrowRecordDTO entityToDto(BorrowRecord entity);

    default Book mapBookFromId(Integer id) {
        if (id == null) {
            return null;
        }
        Book book = new Book();
        book.setId(id);
        return book;
    }

    default Borrower mapBorrowerFromId(Integer id) {
        if (id == null) {
            return null;
        }
        Borrower borrower = new Borrower();
        borrower.setId(id);
        return borrower;
    }

    @Named("formatDateTime")
    default String formatDateTime(LocalDateTime localDateTime) {
        String localDateTimeString = "";
        if(localDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            localDateTimeString = localDateTime.format(formatter);
        }

        return localDateTimeString;
    }

    @Named("parseDateTime")
    default LocalDateTime parseDateTime(String dateTimeString) {
        LocalDateTime localDateTime = null;
        if(dateTimeString != null && dateTimeString.isBlank()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            localDateTime = LocalDateTime.parse(dateTimeString, formatter);
        }

        return localDateTime;
    }
}

