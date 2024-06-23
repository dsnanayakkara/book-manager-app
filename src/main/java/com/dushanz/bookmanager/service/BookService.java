package com.dushanz.bookmanager.service;

import com.dushanz.bookmanager.dto.BookDTO;
import com.dushanz.bookmanager.dto.BookResultDTO;
import com.dushanz.bookmanager.entity.Book;
import com.dushanz.bookmanager.entity.BookInfo;
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
     * Returns a result dto object containing a list of books that are currently available for borrow.
     * result is filtered based on unique flag, if unique is true, fetch the available books that has distinct ISBN.
     * otherwise return all available books including copies of the same book
     *
     * @return a list books registered and currently available for borrowing in the library.
     */
    public BookResultDTO getBooksAvailableForBorrow(boolean unique) {
        List<BookDTO> bookDTOs;
        if (unique) {
            List<BookInfo> bookCatalog = bookInfoRepository.findAll();
            List<BookInfo> availableCatalog = bookCatalog.stream()
                    .filter(bookInfo -> bookInfo.getBookCopies().stream()
                            .anyMatch(book -> !book.getIsBorrowed()))
                    .toList();
            bookDTOs = availableCatalog.stream()
                    .map(BookInfoMapper.INSTANCE::toDto)
                    .toList();
        } else {
            List<Book> availableBooks = bookRepository.findByIsBorrowed(Boolean.FALSE);
            bookDTOs = availableBooks.stream()
                    .map(BookMapper.INSTANCE::entityToDto)
                    .toList();
        }
        return createBookResultDTO(bookDTOs);
    }

    /**
     * Returns the details of all the books in the library, regardless of availability
     * result is filtered based on unique flag, if unique is true, fetch all books that has distinct ISBN
     * if unique is false, fetch all registered books including copies of the same book
     *
     * @return complete list of books in the library.
     */
    public BookResultDTO getAllBooks(boolean unique) {
        List<Book> allBooksList = bookRepository.findAll();
        List<BookDTO> bookDTOs;
        if (unique) {
            List<Book> uniqueBooks = allBooksList.stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toMap(
                                    book -> book.getBookInfo().getIsbn(),
                                    book -> book,
                                    (book1, book2) -> book1
                            ),
                            map -> new ArrayList<>(map.values())
                    ));
            bookDTOs = uniqueBooks.stream()
                    .map(BookMapper.INSTANCE::entityToDto)
                    .toList();
        } else {
            bookDTOs = allBooksList.stream()
                    .map(BookMapper.INSTANCE::entityToDto)
                    .toList();
        }
        return createBookResultDTO(bookDTOs);
    }

    private BookResultDTO createBookResultDTO(List<BookDTO> bookDTOs) {
        BookResultDTO result = new BookResultDTO();
        result.setResult(bookDTOs);
        result.setTotalBooks(bookDTOs.size());
        return result;
    }
}
