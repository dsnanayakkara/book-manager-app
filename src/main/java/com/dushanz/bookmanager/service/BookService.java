package com.dushanz.bookmanager.service;

import com.dushanz.bookmanager.dto.BookDTO;
import com.dushanz.bookmanager.entity.Book;
import com.dushanz.bookmanager.entity.BookInfo;
import com.dushanz.bookmanager.mapper.BookInfoMapper;
import com.dushanz.bookmanager.mapper.BookMapper;
import com.dushanz.bookmanager.repository.BookInfoRepository;
import com.dushanz.bookmanager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookInfoRepository bookInfoRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookInfoRepository bookInfoRepository, BookRepository bookRepository) {
        this.bookInfoRepository = bookInfoRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Register a new book to the library.
     *
     * @param bookDTO the DTO object containing the information of the newly registered book
     * @return the new book details that was added to the library.
     */
    public BookDTO registerNewBook(BookDTO bookDTO) {
        // Find the book info by ISBN
        Optional<BookInfo> existingBookInfo = bookInfoRepository.findByIsbn(bookDTO.getIsbn());

        // If the book info doesn't exist, save the new book info
        // if the book info is already there that means we're adding a new copy of an existing book
        // therefore no need to create book info again(ISBN, title, author)
        if (existingBookInfo.isEmpty()) {
            BookInfo bookInfo = BookInfoMapper.INSTANCE.toEntity(bookDTO);
            existingBookInfo = Optional.of(bookInfoRepository.save(bookInfo));
        }
        // Create a new book with the existing or new book info
        Book book = new Book();
        book.setBookInfo(existingBookInfo.get());
        // Save and return the new book as a DTO
        Book newBook = bookRepository.save(book);
        return BookMapper.INSTANCE.entityToDto(newBook);
    }

    /**
     * Returns the total catalog of unique books registered in the library
     *
     * @return a list of unique books registered in the library.
     */
    public List<BookDTO> getTotalBookCatalog() throws Exception {
        List<BookInfo> bookCatalog = bookInfoRepository.findAll();

        if (bookCatalog.isEmpty()) {
            throw new Exception("Library is empty !");
        }

        // return converted list of Book DTO's
        return bookCatalog.stream()
                .map(BookInfoMapper.INSTANCE::toDto)
                .toList();
    }

    /**
     * Returns the available catalog of unique books registered in the library.
     * Does not contain currently borrowed books.
     *
     * @return a list of unique books registered in the library.
     */
    public List<BookDTO> getAvailableBookCatalog() throws Exception {
        List<BookInfo> bookCatalog = bookInfoRepository.findAll();

        if (bookCatalog.isEmpty()) {
            throw new Exception("Library is empty !");
        }

        // return converted list of Book DTO's
        return bookCatalog.stream()
                .filter(bookInfo -> !bookInfo.getBookCopies().isEmpty())
                .map(BookInfoMapper.INSTANCE::toDto)
                .toList();
    }

    /**
     * Returns the details of all the books in the library, including copies of the same book
     *
     * @return complete list of books in the library.
     */
    public List<BookDTO> getAllBooks() throws Exception {
        List<Book> allBooksList = bookRepository.findAll();

        if (allBooksList.isEmpty()) {
            throw new Exception("Library is empty !");
        }

        // return converted list of Book DTO's
        return allBooksList.stream()
                .map(BookMapper.INSTANCE::entityToDto)
                .toList();
    }
}
