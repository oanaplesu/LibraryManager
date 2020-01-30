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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.attribute.UserPrincipal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


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
        List<Loan> loanReturnedList = loanService.filterByUserIdReturned(userId);
        List<Pair<Loan, NotReturnedStruct>> loanNotReturnedList = loanService.filterByUserIdNotReturned(userId);

        model.addAttribute("userId", userId);
        model.addAttribute("loanNotReturnedList", loanNotReturnedList);
        model.addAttribute("loanReturnedList", loanReturnedList);
        return "loan/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteLoan(@PathVariable long id, @RequestParam long userId) {
        getLoanOrThrow404(id);
        loanService.delete(id);
        return "redirect:/loan/view?userId=" + userId + "&deleteSuccess";
    }

    @PostMapping("/new")
    public String newLoan(@RequestParam long userId, @RequestParam String bookcopyId,
                          Model model) {
        Optional<BookCopy> bookCopy = bookcopyService.get(bookcopyId);
        if (bookCopy.isEmpty()) {
            return "redirect:/loan/view?userId=" + userId + "&showForm&showError";
        }

        Optional<User> user = userService.get(userId);
        if(user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User-ul cu id-ul mentionat nu exista");
        }

        Loan loan = new Loan();
        loan.setUser(user.get());
        loan.setBookcopy(bookCopy.get());
        loan.setNotReturned(false);
        loan.setNotReturned(true);
        loanService.save(loan);

        return "redirect:/loan/view?userId="+ userId + "&addSuccess";
    }

    @GetMapping("/mark_returned/{id}")
    private String markAsReturned(@PathVariable long id, @RequestParam long userId) {
        Loan loan = getLoanOrThrow404(id);
        loan.setNotReturned(false);
        loan.setReturnedDate(LocalDateTime.now());
        loanService.save(loan);
        return "redirect:/loan/view?userId=" + userId + "&markSuccess";
    }

    private Loan getLoanOrThrow404(Long id) {
        Optional<Loan> loan =  loanService.get(id);
        if(loan.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nu s-a gasit resursa ceruta");
        }
        return loan.get();
    }
}
