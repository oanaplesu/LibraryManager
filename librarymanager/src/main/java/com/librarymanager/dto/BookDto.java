package com.librarymanager.dto;

import com.librarymanager.misc.FieldMatch;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.*;

public class BookDto {
    private Long id;

    @NotEmpty(message = "Campul nu poate fi gol")
    private String title;

    @NotEmpty(message = "Campul nu poate fi gol")
    private String author;

    @Min(value = 0, message = "Numarul de pagini nu este valid")
    private Integer nrPages;

    @NotEmpty(message = "Campul nu poate fi gol")
    private String publishingHouse;

    @Digits(integer = 4,fraction = 0, message = "Introduceti un an valid")
    @NotEmpty(message = "Campul nu poate fi gol")
    private String year;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getNrPages() {
        return nrPages;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public String getYear() {
        return year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setNrPages(Integer nrPages) {
        this.nrPages = nrPages;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
