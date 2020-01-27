package com.librarymanager.controllers;

import com.librarymanager.entities.Book;
import com.librarymanager.entities.BookCopy;
import com.librarymanager.entities.Loan;
import com.librarymanager.entities.User;
import com.librarymanager.misc.NotReturnedStruct;
import com.librarymanager.services.BookCopyService;
import com.librarymanager.services.LoanService;
import com.librarymanager.services.UserService;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;


import java.time.temporal.ChronoUnit;


@Controller
@RequestMapping("/loan")
public class LoansController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookCopyService bookcopyService;

    @GetMapping("/view")
    public String listLoansForUserId(@RequestParam Long userId, Model model) {
        List<Loan> loanListNotReturned = loanService.filterByUserIdNotReturned(userId);
        List<Loan> loanListReturned = loanService.filterByUserIdReturned(userId);
        ArrayList<Pair<Loan, NotReturnedStruct>> newList = new ArrayList<>();

        for(Loan loan: loanListNotReturned) {
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


        model.addAttribute("userId", userId);
        model.addAttribute("loanListNotReturned", newList);
        model.addAttribute("loanListReturned", loanListReturned);
        return "loanall";
    }

    @GetMapping("/delete/{id}")
    public String deleteLoan(@PathVariable long id, @RequestParam long userId) {
        loanService.delete(id);
        return "redirect:/loan/view?userId=" + userId;
    }

    @PostMapping("/new")
    public String newLoan(@RequestParam long userId, @RequestParam String bookcopyId) {
        BookCopy bookcopy = bookcopyService.get(bookcopyId);
        User user = userService.get(userId);

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBookcopy(bookcopy);
        loan.setNotReturned(false);
        loan.setNotReturned(true);
        loanService.save(loan);

        return "redirect:/loan/view?userId="+ userId;
    }

    @GetMapping("/mark_returned/{id}")
    private String markAsReturned(@PathVariable long id, @RequestParam long userId) {
        Loan loan = loanService.get(id);
        loan.setNotReturned(false);
        loan.setReturnedDate(LocalDateTime.now());
        loanService.save(loan);
        return "redirect:/loan/view?userId=" + userId;
    }

//    private User getLoggedInUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//        return userService.findByEmail(currentPrincipalName);
//    }
}
