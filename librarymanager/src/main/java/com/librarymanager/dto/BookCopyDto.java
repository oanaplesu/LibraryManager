package com.librarymanager.dto;

import com.librarymanager.entities.Book;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BookCopyDto {
    @NotNull
    private Long bookId;

    @NotEmpty(message = "Campul nu poate fi gol")
    private String id;

    @NotEmpty(message = "Campul nu poate fi gol")
    private String branchName;

    private Book book;

    public Long getBookId() {
        return bookId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
