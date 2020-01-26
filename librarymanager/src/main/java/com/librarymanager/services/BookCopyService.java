package com.librarymanager.services;

import com.librarymanager.entities.Book;
import com.librarymanager.entities.BookCopy;
import com.librarymanager.misc.BookCopyStats;
import com.librarymanager.repositories.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BookCopyService {
    @Autowired
    private BookCopyRepository repo;

    public List<BookCopy> listAll() {
        return repo.findAll();
    }

    public void save(BookCopy bookcopy) {
        repo.save(bookcopy);
    }

    public BookCopy get(String id) {
        return repo.findById(id).get();
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public List<BookCopy> getAllBookCopiesByBookId(long bookId) {
        return repo.findByBookId(bookId);
    }

    public List<BookCopyStats> getBookCopyStatsForBookId(long bookId) {
        return repo.getBookCopyStatsForBookId(bookId);
    }
}
