package com.librarymanager.services;

import com.librarymanager.entities.Book;
import com.librarymanager.entities.Loan;
import com.librarymanager.repositories.BookRepository;
import com.librarymanager.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class LoanService {
    @Autowired
    private LoanRepository repo;

    public List<Loan> listAll() {
        return repo.findAll();
    }

    public void save(Loan loan) {
        repo.save(loan);
    }

    public Loan get(long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) {
        repo.deleteById(id);
    }

    public List<Loan> filterByUserIdReturned(long id) {
        return repo.findByUserIdAndNotReturned(id, false);
    }

    public List<Loan> filterByUserIdNotReturned(long id) {
        return repo.findByUserIdAndNotReturned(id, true);
    }
}
