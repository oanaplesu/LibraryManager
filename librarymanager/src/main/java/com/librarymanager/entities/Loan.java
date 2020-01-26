package com.librarymanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bookcopy_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private BookCopy bookCopy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    private String loanDate;

    private boolean notReturned;

    public int getId() {
        return id;
    }

    public BookCopy getBookcopy() {
        return bookCopy;
    }

    public User getUser() {
        return user;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public boolean isNotReturned() {
        return notReturned;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBookcopy(BookCopy bookcopy) {
        this.bookCopy = bookcopy;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public void setNotReturned(boolean notReturned) {
        this.notReturned = notReturned;
    }
}
