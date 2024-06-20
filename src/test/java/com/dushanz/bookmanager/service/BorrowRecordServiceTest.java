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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class BorrowRecordServiceTest {

    @Mock
    private BorrowRecordMapper mapper;

    @Mock
    private EntityManager entityManager;

    @Mock
    private BorrowRecordRepository borrowRecordRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @Mock
    private BookRepository bookRepository;


    @InjectMocks
    private BorrowRecordService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateBorrowRecord() throws Exception {

        BorrowRecordDTO dto = new BorrowRecordDTO();
        dto.setBookId(1);
        dto.setBorrowerId(1);
        dto.setBorrowDate(LocalDateTime.now());

        Book book = new Book();
        book.setId(1);
        book.setStatus(Boolean.TRUE); // The book is available

        Borrower borrower = new Borrower();
        borrower.setId(1);


        when(entityManager.find(Book.class, dto.getBookId(), LockModeType.PESSIMISTIC_WRITE)).thenReturn(book);
        when(borrowerRepository.findById(dto.getBorrowerId())).thenReturn(Optional.of(borrower));
        when(bookRepository.save(any(Book.class))).thenAnswer(i -> i.getArguments()[0]);
        when(mapper.dtoToEntity(dto)).thenReturn(new BorrowRecord());
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenAnswer(i -> {
            BorrowRecord saved = (BorrowRecord) i.getArguments()[0];
            saved.setId(1);
            return saved;
        });


        BorrowRecordDTO actualDto = service.borrowBook(dto);

        assertEquals(dto.getBorrowerId(), actualDto.getBorrowerId());
        assertEquals(dto.getBookId(), actualDto.getBookId());
        assertNotNull(actualDto.getBorrowDate());
        verify(entityManager).find(Book.class, dto.getBookId(), LockModeType.PESSIMISTIC_WRITE);
        verify(borrowerRepository).findById(dto.getBorrowerId());
        verify(borrowRecordRepository).save(any(BorrowRecord.class));
    }

    @Test
    void testBorrowBook_WhenBookIsUnavailable() {

        BorrowRecordDTO dto = new BorrowRecordDTO();
        dto.setBookId(1);
        dto.setBorrowerId(1);
        dto.setBorrowDate(LocalDateTime.now());

        Book book = new Book();
        book.setId(1);
        book.setStatus(Boolean.FALSE); // The book is not available

        Borrower borrower = new Borrower();
        borrower.setId(1);

        when(entityManager.find(Book.class, dto.getBookId(), LockModeType.PESSIMISTIC_WRITE)).thenReturn(book);
        when(borrowerRepository.findById(dto.getBorrowerId())).thenReturn(Optional.of(borrower));
        when(bookRepository.save(any(Book.class))).thenAnswer(i -> i.getArguments()[0]);
        // Expecting exception as the book is not available to borrow
        assertThrows(IllegalStateException.class, () -> {
            service.borrowBook(dto);
        });

        verify(entityManager).find(Book.class, dto.getBookId(), LockModeType.PESSIMISTIC_WRITE);
        verify(borrowerRepository).findById(dto.getBorrowerId());
        verify(bookRepository, never()).save(any(Book.class));
        verify(borrowRecordRepository, never()).save(any(BorrowRecord.class));

    }

}



