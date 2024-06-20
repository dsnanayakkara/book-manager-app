package com.dushanz.bookmanager.repository;

import com.dushanz.bookmanager.entity.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookInfoRepository extends JpaRepository<BookInfo, Integer> {
    Optional<BookInfo> findByIsbn(String isbn);
}
