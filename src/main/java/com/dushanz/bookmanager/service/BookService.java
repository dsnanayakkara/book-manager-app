package com.dushanz.bookmanager.service;

import com.dushanz.bookmanager.dto.BookDTO;
import com.dushanz.bookmanager.dto.BookResultDTO;
import com.dushanz.bookmanager.entity.Book;
import com.dushanz.bookmanager.entity.BookInfo;
import com.dushanz.bookmanager.exception.ResourceNotFoundException;
import com.dushanz.bookmanager.mapper.BookInfoMapper;
import com.dushanz.bookmanager.mapper.BookMapper;
import com.dushanz.bookmanager.repository.BookInfoRepository;
import com.dushanz.bookmanager.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookInfoRepository bookInfoRepository;
    private final BookRepository bookRepository;
    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);

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

        else {
            // we found a book with same ISBN as an existing book. Therefore, we Need to validate author and title
            BookInfo bookInfo = existingBookInfo.get();
            if (!(bookInfo.getAuthor().equals(bookDTO.getAuthor()) && bookInfo.getTitle().equals(bookDTO.getTitle()))) {
                LOG.error("Book with an identical isbn {} with different author/title found", bookDTO.getIsbn());
                throw new IllegalArgumentException("Book with an identical isbn with different author/title found");

            }
        }
        // Create a new book with the existing or new book info
        Book book = new Book();
        book.setBookInfo(existingBookInfo.get());
        // Save and return the new book as a DTO
        Book newBook = bookRepository.save(book);
        return BookMapper.INSTANCE.entityToDto(newBook);
    }

    /**
     * Returns a result dto object containing a list of books based on the boolean filter unique
     * if unique is true, then return the catalog of available books
     * otherwise return all available books(i.e. books that are not borrowed) including copies of the same book
     *
     * @return a list books registered and currently available for borrowing in the library.
     */
    public BookResultDTO getBooksAvailableForBorrow(boolean unique) {
        List<BookInfo> bookCatalog;
        List<BookInfo> filteredBookInfos;
        List<Book> availableBooks;
        List<BookDTO> bookDTOs;
        BookResultDTO result = new BookResultDTO();
        // fetch unique books that are not borrowed yet
        if (unique) {
            bookCatalog = bookInfoRepository.findAll();

            filteredBookInfos = bookCatalog.stream()
                    .filter(bookInfo -> bookInfo.getBookCopies().stream()
                            .anyMatch(book -> !book.getIsBorrowed()))
                    .toList();

            bookDTOs = filteredBookInfos.stream()
                    .map(BookInfoMapper.INSTANCE::toDto)
                    .toList();
        } else {
            // fetch all books including copies, that are not borrowed yet
            availableBooks = bookRepository.findByIsBorrowed(Boolean.FALSE);
            if (Objects.isNull(availableBooks) || availableBooks.isEmpty()) {
                throw new ResourceNotFoundException("Book", "", "");
            }
            // Convert the Book entities to DTOs
            bookDTOs = availableBooks.stream()
                    .map(BookMapper.INSTANCE::entityToDto)
                    .toList();

        }
        result.setResult(bookDTOs);
        result.setTotalBooks(bookDTOs.size());
        return result;
    }

    /**
     * Returns the details of all the books in the library, including copies of the same book
     *
     * @return complete list of books in the library.
     */
    public BookResultDTO getAllBooks(boolean unique)  {
        List<Book> allBooksList = bookRepository.findAll();
        List<BookDTO> bookDTOs;
        BookResultDTO result = new BookResultDTO();

        if (allBooksList.isEmpty()) {
            throw new ResourceNotFoundException("Book", "", "");
        }

        if (unique) {
            // extract unique books from the books list
            List<Book> uniqueBooks = allBooksList.stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toMap(
                                    book -> book.getBookInfo().getIsbn(),
                                    book -> book,
                                    (book1, book2) -> book1
                            ),
                            map -> new ArrayList<>(map.values())
                    ));

            // convert to relevant DTO list
            bookDTOs = uniqueBooks.stream()
                    .map(BookMapper.INSTANCE::entityToDto)
                    .toList();
        } else {
            // Include all the books regardless of having copies of the same book
            bookDTOs = allBooksList.stream()
                    .map(BookMapper.INSTANCE::entityToDto)
                    .toList();
        }
        result.setResult(bookDTOs);
        result.setTotalBooks(bookDTOs.size());

        return result;
    }
}
