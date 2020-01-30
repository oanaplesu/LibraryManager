package com.librarymanager.services;

import com.librarymanager.dto.BookDto;
import com.librarymanager.entities.Book;
import com.librarymanager.misc.BookSpecification;
import com.librarymanager.misc.SearchCriteria;
import com.librarymanager.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    @Autowired
    private BookRepository repo;

    public List<Book> listAll() {
        return repo.findAll();
    }

    public void save(Book book) {
        repo.save(book);
    }

    public Optional<Book> get(long id) {
        return repo.findById(id);
    }

    public void delete(long id) {
        repo.deleteById(id);
    }

    public Page<Book> filterAndPaginate(int page, String title, String author, String year) {
        List<BookSpecification> filters = new ArrayList<>();

        if(title != null) {
            filters.add(new BookSpecification(new SearchCriteria("title", ":", title)));
        }

        if(author != null) {
            filters.add(new BookSpecification(new SearchCriteria("author", ":", author)));
        }

        if(year != null) {
            filters.add(new BookSpecification(new SearchCriteria("year", ":", year)));
        }

        Specification<Book> spec = null;

        for (BookSpecification filter: filters) {
            spec = ((spec == null) ? Specification.where(filter) : spec.and(filter));
        }

        PageRequest pageable = PageRequest.of(page - 1 , 12, Sort.by("createdAt").descending());

        if(spec != null) {
            return repo.findAll(spec, pageable);
        }

        return repo.findAll(pageable);
    }

    public void save(BookDto bookDto, String imagePath) {
        Book book = new Book();
        saveEditedBook(book, bookDto, imagePath);
    }

    public void saveEditedBook(Book book, BookDto bookDto, String imagePath)
    {
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setNrPages(bookDto.getNrPages());
        book.setPublishingHouse(bookDto.getPublishingHouse());
        book.setYear(bookDto.getYear());

        if(!imagePath.isEmpty()) {
            book.setImagePath(imagePath);
        }
        repo.save(book);
    }
}
