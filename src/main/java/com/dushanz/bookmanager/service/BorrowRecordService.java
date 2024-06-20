package com.dushanz.bookmanager.service;

import com.dushanz.bookmanager.dto.BorrowRecordDTO;
import com.dushanz.bookmanager.entity.BorrowRecord;
import com.dushanz.bookmanager.repository.BorrowRecordRepository;
import com.dushanz.bookmanager.mapper.BorrowRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowRecordService {
    private final BorrowRecordRepository borrowRecordRepository;
    private final BorrowRecordMapper mapper;

    @Autowired
    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository, BorrowRecordMapper mapper) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.mapper = mapper;
    }

    /**
     * Creates an entry in the database for a borrowed book.
     * @param borrowRecordDTO the borrowed book details that needs to be persisted
     * @return the borrowed record that was persisted in the DB.
     */
    public BorrowRecordDTO createBorrowRecord(BorrowRecordDTO borrowRecordDTO) {
        // map the BorrowRecordDTO into BorrowRecord entity using MapStruct
        BorrowRecord recordEntity = mapper.dtoToEntity(borrowRecordDTO);
        BorrowRecord savedRecord = borrowRecordRepository.save(recordEntity);
        // map the BorrowRecord(entity) into BorrowRecordDTO using MapStruct
        return mapper.entityToDto(savedRecord);
    }

}
