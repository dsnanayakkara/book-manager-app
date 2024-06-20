package com.dushanz.bookmanager.repository;

import com.dushanz.bookmanager.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByStatus(Boolean status);
}
