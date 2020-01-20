package com.librarymanager.services;

import com.librarymanager.entities.Book;
import com.librarymanager.misc.BookSpecification;
import com.librarymanager.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Book> filterAndPaginate(Specification<Book> spec, Pageable pageable) {
        return repo.findAll(spec, pageable);
    }

    public Page<Book> paginate(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
