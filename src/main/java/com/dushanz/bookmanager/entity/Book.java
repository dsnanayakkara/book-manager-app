package com.dushanz.bookmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Size(max = 20)
    @NotNull
    @Column(name = "ISBN", nullable = false, length = 20)
    private String isbn;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 255)
    @NotNull
    @Column(name = "author", nullable = false)
    private String author;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @OneToMany(mappedBy = "book")
    private Set<BorrowRecord> borrowRecords = new LinkedHashSet<>();

}