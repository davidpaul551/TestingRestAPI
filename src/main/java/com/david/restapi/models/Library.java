package com.david.restapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "LibraryAPI")
public class Library {

    @Column(name="book_name")
    private String bookName;

    @Id
    @Column(name="id")
    private String id;

    @Column(name="isbn")
    private String isbn;

    @Column(name="aisle")
    private int aisle;

    @Column(name="author")
    private String author;

    public String getBook_name() {
        return bookName;
    }

    public void setBook_name(String bookName) {
        this.bookName = bookName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAisle() {
        return aisle;
    }

    public void setAisle(int aisle) {
        this.aisle = aisle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
