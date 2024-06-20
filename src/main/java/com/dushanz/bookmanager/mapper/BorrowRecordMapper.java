package com.dushanz.bookmanager.mapper;

import com.dushanz.bookmanager.dto.BorrowRecordDTO;
import com.dushanz.bookmanager.entity.Book;
import com.dushanz.bookmanager.entity.BorrowRecord;
import com.dushanz.bookmanager.entity.Borrower;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BorrowRecordMapper {
    @Mapping(source = "bookId", target = "book")
    @Mapping(source = "borrowerId", target = "borrower")
    BorrowRecord dtoToEntity(BorrowRecordDTO dto);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "borrower.id", target = "borrowerId")
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
}

