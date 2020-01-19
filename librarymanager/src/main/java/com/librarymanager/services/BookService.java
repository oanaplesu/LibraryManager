package com.librarymanager.services;

import com.librarymanager.entities.Book;
import com.librarymanager.misc.BookSpecification;
import com.librarymanager.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    public Book get(long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) {
        repo.deleteById(id);
    }

    public List<Book> filterAll(Specification<Book> spec) {
        return repo.findAll(spec);
    }
}
