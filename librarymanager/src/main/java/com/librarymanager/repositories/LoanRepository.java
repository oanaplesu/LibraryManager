package com.librarymanager.repositories;

import com.librarymanager.entities.BookCopy;
import com.librarymanager.entities.Loan;
import com.librarymanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByBookCopyId(String bookcopyId);
    List<Loan> findByUserId(Long userId);
}
