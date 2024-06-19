CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

CREATE TABLE IF NOT EXISTS Borrower (
    borrower_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Book (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    ISBN VARCHAR(20) NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    status BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS BorrowRecord (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    borrow_date DATETIME NOT NULL,
    return_date DATETIME,
    borrower_id INT,
    book_id INT,
    FOREIGN KEY (borrower_id) REFERENCES Borrower(borrower_id),
    FOREIGN KEY (book_id) REFERENCES Book(book_id)
);

-- Add indexes
ALTER TABLE Book ADD INDEX idx_status (status);
ALTER TABLE Book ADD INDEX idx_ISBN (ISBN);
ALTER TABLE BorrowRecord ADD INDEX idx_borrower_id (borrower_id);
ALTER TABLE BorrowRecord ADD INDEX idx_book_id (book_id);


-- add test data
INSERT INTO Borrower (name, email) VALUES ('John Doe', 'john.doe@example.com');
INSERT INTO Borrower (name, email) VALUES ('Jane Smith', 'jane.smith@example.com');

-- Insert sample data into the Book table
INSERT INTO Book (ISBN, title, author, status) VALUES ('978-3-16-148410-0', 'Book Title 1', 'Author 1', false);
INSERT INTO Book (ISBN, title, author, status) VALUES ('978-1-40-289460-1', 'Book Title 2', 'Author 2', true);

-- Insert sample data into the BorrowRecord table
-- Assuming 'John Doe' borrowed 'Book Title 1' and hasn't returned it yet
INSERT INTO BorrowRecord (borrow_date, return_date, borrower_id, book_id) VALUES (NOW(), NULL, 1, 1);

-- Assuming 'Jane Smith' borrowed 'Book Title 2' and returned it
INSERT INTO BorrowRecord (borrow_date, return_date, borrower_id, book_id) VALUES (DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 2, 2);

