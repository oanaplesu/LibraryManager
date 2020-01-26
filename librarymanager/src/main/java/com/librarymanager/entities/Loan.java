package com.librarymanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bookcopy_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private BookCopy bookcopy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    private boolean notReturned;

    @CreationTimestamp
    private LocalDateTime loanDate;

    private LocalDateTime returnedDate;

    public long getId() {
        return id;
    }

    public BookCopy getBookcopy() {
        return bookcopy;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public LocalDateTime getReturnedDate() {
        return returnedDate;
    }

    public boolean isNotReturned() {
        return notReturned;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBookcopy(BookCopy bookcopy) {
        this.bookcopy = bookcopy;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNotReturned(boolean notReturned) {
        this.notReturned = notReturned;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public void setReturnedDate(LocalDateTime returnedDate) {
        this.returnedDate = returnedDate;
    }
}
