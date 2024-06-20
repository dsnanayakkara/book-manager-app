package com.dushanz.bookmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @OneToMany(mappedBy = "book")
    private Set<BorrowRecord> borrowRecords = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_info_id")
    private BookInfo bookInfo;

}