package com.librarymanager.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String author;
    private int nrPages;
    private String publishingHouse;
    private String year;
    private String imagePath;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Book() {
    }

    public long getId() {
        return id;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getNrPages() {
        return nrPages;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public String getYear() {
        return year;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setNrPages(int nrPages) {
        this.nrPages = nrPages;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
