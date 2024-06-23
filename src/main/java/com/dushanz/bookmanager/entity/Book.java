package com.dushanz.bookmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "is_borrowed", nullable = false)
    private Boolean isBorrowed = false;

    @OneToMany(mappedBy = "book")
    private Set<BorrowRecord> borrowRecords = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_info_id")
    private BookInfo bookInfo;

    public Book(Integer id, Boolean isBorrowed, Set<BorrowRecord> borrowRecords, BookInfo bookInfo) {
        this.id = id;
        this.isBorrowed = isBorrowed;
        this.borrowRecords = borrowRecords;
        this.bookInfo = bookInfo;
    }

    public Book() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}