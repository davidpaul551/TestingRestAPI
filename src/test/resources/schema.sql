DROP TABLE IF EXISTS LibraryAPI;

CREATE TABLE LibraryAPI (
    id VARCHAR(255) PRIMARY KEY,
    book_name VARCHAR(255),
    isbn VARCHAR(255),
    aisle INT,
    author VARCHAR(255)
);
