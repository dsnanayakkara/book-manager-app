package com.dushanz.bookmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * BookInfo entity represents a distinct book in the library based on ISBN.
 */
@Getter
@Setter
@Entity
@Table(name="book_info")
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

    @OneToMany(mappedBy = "bookInfo")
    private Set<Book> bookCopies = new LinkedHashSet<>();

    public BookInfo(Integer id, String isbn, String title, String author, Set<Book> bookCopies) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.bookCopies = bookCopies;
    }

    public BookInfo() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookInfo bookInfo = (BookInfo) o;
        return Objects.equals(isbn, bookInfo.isbn) &&
                Objects.equals(title, bookInfo.title) &&
                Objects.equals(author, bookInfo.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, author);
    }

    public void addBook(Book book) {
        bookCopies.add(book);
    }



}