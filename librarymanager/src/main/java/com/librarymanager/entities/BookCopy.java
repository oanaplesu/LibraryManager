package com.librarymanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class BookCopy {
    @Id
    private String id;
    private String branchName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Book book;

    public String getId() {
        return id;
    }

    public String getBranchName() {
        return branchName;
    }

    public Book getBook() {
        return book;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
