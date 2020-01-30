package com.librarymanager.services;

import com.librarymanager.entities.Book;
import com.librarymanager.entities.Loan;
import com.librarymanager.misc.NotReturnedStruct;
import com.librarymanager.repositories.BookRepository;
import com.librarymanager.repositories.LoanRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<Loan> get(long id) {
        return repo.findById(id);
    }

    public void delete(long id) {
        repo.deleteById(id);
    }

    public List<Loan> filterByUserIdReturned(long id) {
        return repo.findByUserIdAndNotReturned(id, false);
    }

    public List<Pair<Loan, NotReturnedStruct>> filterByUserIdNotReturned(long id) {
        List<Loan> loanNotReturnedList = repo.findByUserIdAndNotReturned(id, true);
        ArrayList<Pair<Loan, NotReturnedStruct>> newList = new ArrayList<>();

        for(Loan loan: loanNotReturnedList) {
            NotReturnedStruct struct = new NotReturnedStruct();
            struct.setDeadline(loan.getLoanDate().plusMonths(1));
            struct.setDeadlineExceeded(struct.getDeadline().toLocalDate().isBefore(LocalDate.now()));
            if(struct.isDeadlineExceeded()) {
                long nrDaysDelay = struct.getDeadline().until(LocalDateTime.now(), ChronoUnit.DAYS) + 1;
                long FINE_PER_DAY = 1;
                long totalFineValue = FINE_PER_DAY * nrDaysDelay;
                struct.setFineValue(totalFineValue);
            } else {
                struct.setFineValue(0);
            }
            newList.add(new ImmutablePair<>(loan, struct));
        }

        return newList;
    }
}
