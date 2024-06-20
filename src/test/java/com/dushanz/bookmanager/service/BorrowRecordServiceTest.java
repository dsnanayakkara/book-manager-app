package com.dushanz.bookmanager.service;

import com.dushanz.bookmanager.dto.BorrowRecordDTO;
import com.dushanz.bookmanager.entity.Book;
import com.dushanz.bookmanager.entity.BorrowRecord;
import com.dushanz.bookmanager.entity.Borrower;
import com.dushanz.bookmanager.repository.BorrowRecordRepository;
import com.dushanz.bookmanager.mapper.BorrowRecordMapper;
import com.dushanz.bookmanager.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BorrowRecordServiceTest {

    @Mock
    private BorrowRecordMapper mapper;

    @Mock
    private BorrowRecordRepository borrowRecordRepository;

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

        BorrowRecord entity = new BorrowRecord();
        Book book = new Book();
        book.setId(1);
        Borrower borrower = new Borrower();
        borrower.setId(1);
        entity.setBook(book);
        entity.setBorrower(borrower);
        entity.setBorrowDate(DateUtils.parseLocalDateTime(dto.getBorrowDate()));

        BorrowRecord savedEntity = new BorrowRecord();
        savedEntity.setId(1);
        savedEntity.setBook(entity.getBook());
        savedEntity.setBorrower(entity.getBorrower());
        savedEntity.setBorrowDate(entity.getBorrowDate());

        BorrowRecordDTO expectedDto = new BorrowRecordDTO();
        expectedDto.setBookId(savedEntity.getBook().getId());
        expectedDto.setBorrowerId(savedEntity.getBorrower().getId());
        expectedDto.setBorrowDate(savedEntity.getBorrowDate());

        // When
        when(mapper.dtoToEntity(dto)).thenReturn(entity);
        when(borrowRecordRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.entityToDto(savedEntity)).thenReturn(expectedDto);

        // Then
        BorrowRecordDTO actualDto = service.borrowBook(dto);

        assertEquals(expectedDto, actualDto);
    }
}

