package com.dushanz.bookmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BookInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_info_id", nullable = false)
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

}