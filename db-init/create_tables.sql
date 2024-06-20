CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

CREATE TABLE IF NOT EXISTS borrower (
    borrower_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS book_info (
    book_info_id INT AUTO_INCREMENT PRIMARY KEY,
    ISBN VARCHAR(20) UNIQUE NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS book (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    book_info_id INT,
    status BOOLEAN NOT NULL,
    FOREIGN KEY (book_info_id) REFERENCES book_info(book_info_id)
);

CREATE TABLE IF NOT EXISTS borrow_record (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    borrow_date DATETIME NOT NULL,
    return_date DATETIME,
    borrower_id INT,
    book_id INT,
    FOREIGN KEY (borrower_id) REFERENCES borrower (borrower_id),
    FOREIGN KEY (book_id) REFERENCES book(book_id)
);

-- Add indexes
ALTER TABLE book ADD INDEX idx_status (status);
ALTER TABLE book ADD INDEX idx_book_info_id (book_info_id);
ALTER TABLE book_info ADD INDEX idx_book_info_ISBN (ISBN);
ALTER TABLE borrow_record ADD INDEX idx_borrower_id (borrower_id);
ALTER TABLE borrow_record ADD INDEX idx_book_id (book_id);
ALTER TABLE borrow_record ADD INDEX idx_borrower_book (borrower_id, book_id);
ALTER TABLE borrow_record ADD INDEX idx_return_date (return_date);


-- add test data
INSERT INTO borrower (name, email) VALUES ('John Doe', 'john.doe@example.com');
INSERT INTO borrower (name, email) VALUES ('Jane Smith', 'jane.smith@example.com');

-- Insert sample data into the BookInfo table
INSERT INTO book_info (ISBN, title, author) VALUES ('978-3-16-148410-0', 'Book Title 1', 'Author 1');
INSERT INTO book_info (ISBN, title, author) VALUES ('978-1-40-289460-1', 'Book Title 2', 'Author 2');

-- Get the ids of the inserted rows
SET @book_info_id1 = (SELECT book_info_id FROM book_info WHERE ISBN = '978-3-16-148410-0');
SET @book_info_id2 = (SELECT book_info_id FROM book_info WHERE ISBN = '978-1-40-289460-1');

-- Insert sample data into the Book table
INSERT INTO book (book_info_id, status) VALUES (@book_info_id1, false);
INSERT INTO book (book_info_id, status) VALUES (@book_info_id2, true);

-- Get the ids of the inserted rows
SET @borrower_id1 = (SELECT borrower_id FROM borrower WHERE email = 'john.doe@example.com');
SET @borrower_id2 = (SELECT borrower_id FROM borrower WHERE email = 'jane.smith@example.com');
SET @book_id1 = (SELECT book_id FROM book WHERE book_info_id = @book_info_id1);
SET @book_id2 = (SELECT book_id FROM book WHERE book_info_id = @book_info_id2);

-- Insert sample data into the BorrowRecord table
-- Assuming 'John Doe' borrowed 'Book Title 1' and hasn't returned it yet
INSERT INTO borrow_record (borrow_date, return_date, borrower_id, book_id) VALUES (NOW(), NULL, @borrower_id1, @book_id1);

-- Assuming 'Jane Smith' borrowed 'Book Title 2' and returned it
INSERT INTO borrow_record (borrow_date, return_date, borrower_id, book_id) VALUES (DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), @borrower_id2, @book_id2);

