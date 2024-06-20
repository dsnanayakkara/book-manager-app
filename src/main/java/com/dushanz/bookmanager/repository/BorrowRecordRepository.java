package com.dushanz.bookmanager.repository;

import com.dushanz.bookmanager.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Integer> {

}
