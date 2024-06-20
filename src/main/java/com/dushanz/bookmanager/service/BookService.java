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
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BookInfoRepository bookInfoRepository;
    private final BookMapper bookMapper;
    private final BookInfoMapper bookInfoMapper;
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookInfoRepository bookInfoRepository, BookMapper bookMapper, BookInfoMapper bookInfoMapper, BookRepository bookRepository) {
        this.bookInfoRepository = bookInfoRepository;
        this.bookMapper = bookMapper;
        this.bookInfoMapper = bookInfoMapper;
        this.bookRepository = bookRepository;
    }

    public BookDTO createBook(BookDTO bookDTO) {
        return null;
    }


    public void deleteBook(Integer id) {

    }

    public BookDTO getBook(Integer id) {
        return null;
    }



    public List<BookDTO> getAllAvailableBooks() {
        return null;
    }


    /**
     * Register a new book to the library.
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
            BookInfo bookInfo = bookInfoMapper.toEntity(bookDTO);
            existingBookInfo = Optional.of(bookInfoRepository.save(bookInfo));
        }
        // Create a new book with the existing or new book info
        Book book = new Book();
        book.setBookInfo(existingBookInfo.get());
        // Save and return the new book as a DTO
        Book newBook =  bookRepository.save(book);
        return bookMapper.entityToDto(newBook);
    }

    /**
     * Returns the total catalog of unique books registered in the library
     * @return a list of unique books registered in the library.
     */
    public List<BookDTO> getTotalBookCatalog() throws Exception {
        List<BookInfo> bookCatalog = bookInfoRepository.findAll();

        if(bookCatalog.isEmpty()) {
            throw new Exception("Library is empty !");
        }

        // return converted list of Book DTO's
        return bookCatalog.stream()
                .map(bookInfoMapper::toDto)
                .toList();
    }

    /**
     * Returns the available catalog of unique books registered in the library.
     * Does not contain currently borrowed books.
     * @return a list of unique books registered in the library.
     */
    public List<BookDTO> getAvailableBookCatalog() throws Exception {
        List<BookInfo> bookCatalog = bookInfoRepository.findAll();

        if(bookCatalog.isEmpty()) {
            throw new Exception("Library is empty !");
        }

        // return converted list of Book DTO's
        return bookCatalog.stream()
                .filter(bookInfo -> !bookInfo.getBookCopies().isEmpty())
                .map(bookInfoMapper::toDto)
                .toList();
    }

    /**
     * Returns the details of all the books in the library, including copies of the same book
     * @return complete list of books in the library.
     */
    public List<BookDTO> getAllBooks() throws Exception {
        List<Book> allBooksList = bookRepository.findAll();

        if(allBooksList.isEmpty()) {
            throw new Exception("Library is empty !");
        }

        // return converted list of Book DTO's
        return allBooksList.stream()
                .map(bookMapper::entityToDto)
                .toList();
    }

    /**
     * Update the details of an existing book in the library.
     * Useful for fixing incorrect book records or amending missing information
     * @param id unique id of the book that needs to be updated
     * @param bookDTO the DTO object containing the information of the book that needs to be updated
     * @throws Exception if Book matching the id is not found
     * @return the updated book details.
     */
    public BookDTO updateBook(Integer id, BookDTO bookDTO) throws Exception {
        // Find the book by id, if not found throw exception as we cannot fulfil the request
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new Exception("Book not found"));

        BookInfo existingBookInfo = existingBook.getBookInfo();

        // Find the book info by id, if not found throw exception as we cannot fulfil the request
        if(Objects.isNull(existingBookInfo) ) {
            throw new Exception("BookInfo not found for the book with id " + id);
        }

        // Check each field and update if changed
        if (!Objects.equals(existingBookInfo.getTitle(), bookDTO.getTitle())) {
            existingBookInfo.setTitle(bookDTO.getTitle());
        }
        if (!Objects.equals(existingBookInfo.getAuthor(), bookDTO.getAuthor())) {
            existingBookInfo.setAuthor(bookDTO.getAuthor());
        }
        if (!Objects.equals(existingBookInfo.getIsbn(), bookDTO.getIsbn())) {
            existingBookInfo.setIsbn(bookDTO.getIsbn());
        }

        // Update and return the new book as a DTO
        BookInfo updatedInfo =  bookInfoRepository.save(existingBookInfo);
        return bookInfoMapper.toDto(updatedInfo);
    }
}
