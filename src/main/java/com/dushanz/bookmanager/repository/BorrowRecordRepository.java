package com.dushanz.bookmanager.repository;

import com.dushanz.bookmanager.entity.Book;
import com.dushanz.bookmanager.entity.BorrowRecord;
import com.dushanz.bookmanager.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Integer> {

    /**
     * Returns an optional BorrowRecord for a given Book that has not been returned yet by the Borrower.
     * @param book Book entity
     * @param borrower Borrower entity
     * @return Optional of BorrowRecord
     */
    Optional<BorrowRecord> findByBookAndBorrowerAndReturnDateIsNull(Book book, Borrower borrower);


}
