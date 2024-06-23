package com.dushanz.bookmanager.service;

import com.dushanz.bookmanager.dto.BookResultDTO;
import com.dushanz.bookmanager.entity.Book;
import com.dushanz.bookmanager.entity.BookInfo;
import com.dushanz.bookmanager.repository.BookInfoRepository;
import com.dushanz.bookmanager.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookInfoRepository bookInfoRepository;

    @InjectMocks
    private BookService bookService;

    private static List<Book> setUpBooksForTest_noneBorrowed() {
        List<Book> registeredBooks = new ArrayList<>();
        BookInfo bookInfo1 = new BookInfo(1, "9788845292613", "The Lord of the Rings", "Tolkien, John R. R", new HashSet<>());
        BookInfo bookInfo2 = new BookInfo(2, "9780261103689", "The Lord of the Rings", "Tolkien, John R. R", new HashSet<>());
        BookInfo bookInfo3 = new BookInfo(3, "9780007237500", "A Game of Thrones", "George R. R. Martin", new HashSet<>());
        BookInfo bookInfo4 = new BookInfo(4, "9780007454495", "A Game of Thrones", "George R. R. Martin", new HashSet<>());

        //2 copies of bookInfo1 (ISBN 9788845292613(
        Book book1 = new Book(1, false, new HashSet<>(), bookInfo1);
        Book book2 = new Book(2, false, new HashSet<>(), bookInfo1);
        //1 copy of bookInfo2 (ISBN 9780261103689)
        Book book3 = new Book(3, false, new HashSet<>(), bookInfo2);
        //2 copies of bookInfo3 (ISBN 9780007237500)
        Book book4 = new Book(4, false, new HashSet<>(), bookInfo3);
        Book book5 = new Book(5, false, new HashSet<>(), bookInfo3);
        //1 copy of bookInfo4(ISBN 9780007454495)
        Book book6 = new Book(6, false, new HashSet<>(), bookInfo4);

        // add books back to bookInfo list (many to relationship between book and bookInfo)
        bookInfo1.addBook(book1);
        bookInfo1.addBook(book2);
        bookInfo2.addBook(book3);
        bookInfo3.addBook(book4);
        bookInfo3.addBook(book5);
        bookInfo4.addBook(book6);

        // set up registered book result
        registeredBooks.add(book1);
        registeredBooks.add(book2);
        registeredBooks.add(book3);
        registeredBooks.add(book4);
        registeredBooks.add(book5);
        registeredBooks.add(book6);
        return registeredBooks;
    }

    private static List<Book> setUpBooksForTest_TwoBorrowed() {
        List<Book> registeredBooks = new ArrayList<>();
        BookInfo bookInfo1 = new BookInfo(1, "9788845292613", "The Lord of the Rings", "Tolkien, John R. R", new HashSet<>());
        BookInfo bookInfo2 = new BookInfo(2, "9780261103689", "The Lord of the Rings", "Tolkien, John R. R", new HashSet<>());
        BookInfo bookInfo3 = new BookInfo(3, "9780007237500", "A Game of Thrones", "George R. R. Martin", new HashSet<>());
        BookInfo bookInfo4 = new BookInfo(4, "9780007454495", "A Game of Thrones", "George R. R. Martin", new HashSet<>());

        //2 copies of bookInfo1 (ISBN 9788845292613(
        Book book1 = new Book(1, false, new HashSet<>(), bookInfo1);
        Book book2 = new Book(2, false, new HashSet<>(), bookInfo1);
        //1 copy of bookInfo2 (ISBN 9780261103689)
        Book book3 = new Book(3, false, new HashSet<>(), bookInfo2);
        //2 copies of bookInfo3 (ISBN 9780007237500)
        Book book4 = new Book(4, true, new HashSet<>(), bookInfo3);
        Book book5 = new Book(5, false, new HashSet<>(), bookInfo3);
        //1 copy of bookInfo4(ISBN 9780007454495)
        Book book6 = new Book(6, true, new HashSet<>(), bookInfo4);

        // add books back to bookInfo list (many to relationship between book and bookInfo)
        bookInfo1.addBook(book1);
        bookInfo1.addBook(book2);
        bookInfo2.addBook(book3);
        bookInfo3.addBook(book4);
        bookInfo3.addBook(book5);
        bookInfo4.addBook(book6);

        // set up registered book result
        registeredBooks.add(book1);
        registeredBooks.add(book2);
        registeredBooks.add(book3);
        registeredBooks.add(book5);
        return registeredBooks;
    }

    private static List<Book> setUpBooksForTest_AllBorrowed() {
        List<Book> registeredBooks = new ArrayList<>();
        BookInfo bookInfo1 = new BookInfo(1, "9788845292613", "The Lord of the Rings", "Tolkien, John R. R", new HashSet<>());
        BookInfo bookInfo2 = new BookInfo(2, "9780261103689", "The Lord of the Rings", "Tolkien, John R. R", new HashSet<>());
        BookInfo bookInfo3 = new BookInfo(3, "9780007237500", "A Game of Thrones", "George R. R. Martin", new HashSet<>());
        BookInfo bookInfo4 = new BookInfo(4, "9780007454495", "A Game of Thrones", "George R. R. Martin", new HashSet<>());

        //2 copies of bookInfo1 (ISBN 9788845292613(
        Book book1 = new Book(1, true, new HashSet<>(), bookInfo1);
        Book book2 = new Book(2, true, new HashSet<>(), bookInfo1);
        //1 copy of bookInfo2 (ISBN 9780261103689)
        Book book3 = new Book(3, true, new HashSet<>(), bookInfo2);
        //2 copies of bookInfo3 (ISBN 9780007237500)
        Book book4 = new Book(4, true, new HashSet<>(), bookInfo3);
        Book book5 = new Book(5, true, new HashSet<>(), bookInfo3);
        //1 copy of bookInfo4(ISBN 9780007454495)
        Book book6 = new Book(6, true, new HashSet<>(), bookInfo4);

        // add books back to bookInfo list (many to relationship between book and bookInfo)
        bookInfo1.addBook(book1);
        bookInfo1.addBook(book2);
        bookInfo2.addBook(book3);
        bookInfo3.addBook(book4);
        bookInfo3.addBook(book5);
        bookInfo4.addBook(book6);

        // set up registered book result
        registeredBooks.add(book1);
        registeredBooks.add(book2);
        registeredBooks.add(book3);
        registeredBooks.add(book4);
        registeredBooks.add(book5);
        registeredBooks.add(book6);
        return registeredBooks;
    }

    private static List<Book> setUpBooksForTest_Empty() {
        return new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetBooksAvailableForBorrow_whenNoneBorrowed() {

        List<Book> registeredBooks = setUpBooksForTest_noneBorrowed();

        when(bookRepository.findByIsBorrowed(Boolean.FALSE)).thenReturn(registeredBooks);

        // request available books for borrow, don't care about having distinct ISBN
        BookResultDTO result = bookService.getBooksAvailableForBorrow(false);

        // none of the 6 books are borrowed out, therefore, expecting 6 books to be available
        assertEquals(6, result.getResult().size());
    }

    @Test
    void testGetBooksAvailableForBorrow_whenSomeBorrowed() {

        List<Book> registeredBooks = setUpBooksForTest_TwoBorrowed();

        when(bookRepository.findByIsBorrowed(Boolean.FALSE)).thenReturn(registeredBooks);

        // request available books for borrow, don't care about having distinct ISBN
        BookResultDTO result = bookService.getBooksAvailableForBorrow(false);

        // 2 of the 6 books are borrowed out, therefore, expecting 4 books to be available
        assertEquals(4, result.getResult().size());
    }

    @Test
    void testGetBooksAvailableForBorrow_whenAllBorrowed() {

        List<Book> registeredBooks = setUpBooksForTest_Empty();

        when(bookRepository.findByIsBorrowed(Boolean.FALSE)).thenReturn(registeredBooks);

        // request available books for borrow, don't care about having distinct ISBN
        BookResultDTO result = bookService.getBooksAvailableForBorrow(false);

        // 6 of the 6 books are borrowed out, therefore, expecting 0 books to be available
        assertEquals(0, result.getResult().size());
    }

    @Test
    void testGetBooksAvailableForBorrow_withDistinctIsbn() {

        List<Book> registeredBooks = setUpBooksForTest_noneBorrowed();
        List<BookInfo> distinctBookInfos = registeredBooks.stream()
                .map(Book::getBookInfo)
                .distinct()
                .toList();

        when(bookInfoRepository.findAll()).thenReturn(distinctBookInfos);

        // request available books for borrow that has distinct ISBN
        BookResultDTO result = bookService.getBooksAvailableForBorrow(true);

        //none of the 6 books are borrowed out, but there are 2 copies of ISBN 9788845292613 and two copies of ISBN 9780007237500, therefore, we expect 4 as result
        assertEquals(4, result.getResult().size());
    }

    @Test
    void testGetAllBooks() {

        // let's get a list of books which all have been borrowed out
        List<Book> allBooks = setUpBooksForTest_AllBorrowed();
        // test data has 6 books, in which all are borrowed
        when(bookRepository.findAll()).thenReturn(allBooks);

        BookResultDTO result = bookService.getAllBooks(false);

        // result should list all 6 books regardless of availability(borrowed) status
        assertEquals(allBooks.size(), result.getTotalBooks());
    }

    @Test
    void testGetAllBooks_withDistinctIsbn() {

        // let's get a list of books which all have been borrowed out
        List<Book> allBooks = setUpBooksForTest_AllBorrowed();
        // test data has 6 books, in which all are borrowed
        when(bookRepository.findAll()).thenReturn(allBooks);

        BookResultDTO result = bookService.getAllBooks(true);

        // result should list all 6 books regardless of availability(borrowed) status but filter the copies of the same book, we need books with distinct isbn only
        // in the test data, we have 6 books but 2 of them are copies of the same book, therefore, expecting 4
        assertEquals(4, result.getTotalBooks());
    }
}

