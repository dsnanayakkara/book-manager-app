package com.dushanz.bookmanager.service;

import com.dushanz.bookmanager.dto.BorrowRecordDTO;
import com.dushanz.bookmanager.entity.Book;
import com.dushanz.bookmanager.entity.BorrowRecord;
import com.dushanz.bookmanager.entity.Borrower;
import com.dushanz.bookmanager.repository.BookRepository;
import com.dushanz.bookmanager.repository.BorrowRecordRepository;
import com.dushanz.bookmanager.mapper.BorrowRecordMapper;
import com.dushanz.bookmanager.repository.BorrowerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BorrowRecordService {
    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;
    private final EntityManager entityManager;

    @Autowired
    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository, BookRepository bookRepository, BorrowerRepository borrowerRepository, EntityManager entityManager) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
        this.entityManager = entityManager;
    }

    /**
     * Creates an entry in the database for a borrowed book.
     * @param borrowRecordDTO the borrowed book details that needs to be persisted
     * @return the borrowed record that was persisted in the DB.
     */
    @Transactional
    public BorrowRecordDTO borrowBook(BorrowRecordDTO borrowRecordDTO) throws Exception {
        // Validate the bookId and borrowerId
        Integer bookId = borrowRecordDTO.getBookId();
        Integer borrowerId = borrowRecordDTO.getBorrowerId();

        // Handle concurrency issues where multiple users trying to borrow the same book at the same time
        // using pessimistic locking strategy (i.e lock the table for the duration of transaction)
        Book book = entityManager.find(Book.class, bookId, LockModeType.PESSIMISTIC_WRITE);

        if (book == null) {
            throw new Exception("Book not found");
        }

        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new Exception("Borrower not found"));

        // Check if the book is available to be borrowed
        // status = true indicates book is available, status = false indicates book is unavailable
        if (Boolean.FALSE.equals(book.getStatus())) {
            throw new IllegalStateException("Book is currently not available for borrowing");
        }

        // Create a new BorrowRecord
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setBook(book);
        borrowRecord.setBorrower(borrower);
        borrowRecord.setBorrowDate(LocalDateTime.now());

        // Update the status of the book
        // status = true indicates book is available, status = false indicates book is unavailable
        book.setStatus(Boolean.FALSE);

        // Save the BorrowRecord and the updated Book
        BorrowRecord savedBorrowRecord = borrowRecordRepository.save(borrowRecord);
        bookRepository.save(book);

        // Convert the saved BorrowRecord entity to a DTO and return it
        return BorrowRecordMapper.INSTANCE.entityToDto(savedBorrowRecord);
    }

    @Transactional
    public BorrowRecordDTO returnBook(Integer bookId, Integer borrowerId) throws Exception {
        // Validate the bookId and borrowerId
        Book book = entityManager.find(Book.class, bookId, LockModeType.PESSIMISTIC_WRITE);
        if (book == null) {
            throw new Exception("Book not found");
        }

        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new Exception("Borrower not found"));

        // Check if the book is currently borrowed by the borrower
        BorrowRecord borrowRecord = borrowRecordRepository.findByBookAndBorrowerAndReturnDateIsNull(book, borrower)
                .orElseThrow(() -> new IllegalStateException("The book is not currently borrowed by the borrower"));

        // Update the BorrowRecord and the status of the book
        borrowRecord.setReturnDate(LocalDateTime.now());
        book.setStatus(Boolean.TRUE);

        // Save the updated BorrowRecord and Book
        BorrowRecord updatedBorrowRecord = borrowRecordRepository.save(borrowRecord);
        bookRepository.save(book);

        // Convert the updated BorrowRecord entity to a DTO and return it
        return BorrowRecordMapper.INSTANCE.entityToDto(updatedBorrowRecord);
    }

}
