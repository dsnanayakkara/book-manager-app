package com.dushanz.bookmanager.service;

import com.dushanz.bookmanager.dto.BorrowRecordDTO;
import com.dushanz.bookmanager.entity.Book;
import com.dushanz.bookmanager.entity.BorrowRecord;
import com.dushanz.bookmanager.entity.Borrower;
import com.dushanz.bookmanager.exception.ResourceNotFoundException;
import com.dushanz.bookmanager.repository.BookRepository;
import com.dushanz.bookmanager.repository.BorrowRecordRepository;
import com.dushanz.bookmanager.mapper.BorrowRecordMapper;
import com.dushanz.bookmanager.repository.BorrowerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service class responsible for various CRUD operations for the Records of Books borrowed by users in the system.
 */
@Service
public class BorrowRecordService {
    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;
    private final EntityManager entityManager;
    private static final Logger LOG = LoggerFactory.getLogger(BorrowRecordService.class);

    @Autowired
    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository, BookRepository bookRepository, BorrowerRepository borrowerRepository, EntityManager entityManager) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
        this.entityManager = entityManager;
    }

    /**
     * Creates an entry in the database for a borrowed book.
     *
     * @param bookId unique id of the book being borrowed
     * @param borrowRecordDTO the borrowed book details that needs to be persisted
     * @return the borrowed record that was persisted in the DB.
     */
    @Transactional
    public BorrowRecordDTO borrowBook(Integer bookId, BorrowRecordDTO borrowRecordDTO) {
        // Validate the bookId and borrowerId
        Integer borrowerId = borrowRecordDTO.getBorrowerId();

        // Handle concurrency issues where multiple users trying to borrow the same book at the same time
        // using pessimistic locking strategy (i.e lock the table for the duration of transaction)
        Book book = entityManager.find(Book.class, bookId, LockModeType.PESSIMISTIC_WRITE);

        if (book == null) {
            LOG.error("Book with id {} not found", bookId);
            throw new ResourceNotFoundException("Book", "", "");
        }

        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower", "", ""));

        // Check if the book is available to be borrowed
        // status = true indicates book is available, status = false indicates book is unavailable
        if (Boolean.TRUE.equals(book.getIsBorrowed())) {
            throw new IllegalStateException("Book is currently not available for borrowing");
        }

        // Create a new BorrowRecord
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setBook(book);
        borrowRecord.setBorrower(borrower);
        borrowRecord.setBorrowDate(LocalDateTime.now());

        // Update the isBorrowed status of the book to true as the book is borrowed
        // status = true indicates book is available, status = false indicates book is unavailable
        book.setIsBorrowed(Boolean.TRUE);

        // Save the BorrowRecord and the updated Book
        bookRepository.save(book);
        BorrowRecord savedBorrowRecord = borrowRecordRepository.save(borrowRecord);

        // Convert the saved BorrowRecord entity to a DTO and return it
        return BorrowRecordMapper.INSTANCE.entityToDto(savedBorrowRecord);
    }

    @Transactional
    public BorrowRecordDTO returnBook(Integer bookId, BorrowRecordDTO borrowRecordDTO) {
        // Validate the bookId and borrowerId
        Book book = entityManager.find(Book.class, bookId, LockModeType.PESSIMISTIC_WRITE);
        if (book == null) {
            throw new ResourceNotFoundException("Book", "", "");
        }

        LOG.info("Book with id {} retrieved for return", bookId);

        Borrower borrower = borrowerRepository.findById(borrowRecordDTO.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Borrower", "", ""));

        LOG.info("Borrower with id {} for return", borrower.getId());

        // Check if the book is currently borrowed by the borrower
        BorrowRecord borrowRecord = borrowRecordRepository.findByBookAndBorrowerAndReturnDateIsNull(book, borrower)
                .orElseThrow(() -> new IllegalStateException("The book is not currently borrowed by the borrower"));

        // Update the BorrowRecord (borrowed = false as book is returned) and the status of the book
        borrowRecord.setReturnDate(LocalDateTime.now());
        book.setIsBorrowed(Boolean.FALSE);

        // Save the updated BorrowRecord and Book
        BorrowRecord updatedBorrowRecord = borrowRecordRepository.save(borrowRecord);
        bookRepository.save(book);

        // Convert the updated BorrowRecord entity to a DTO and return it
        return BorrowRecordMapper.INSTANCE.entityToDto(updatedBorrowRecord);
    }

}
