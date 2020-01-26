package com.librarymanager.controllers;

import com.librarymanager.entities.Book;
import com.librarymanager.entities.BookCopy;
import com.librarymanager.entities.Loan;
import com.librarymanager.entities.User;
import com.librarymanager.services.BookCopyService;
import com.librarymanager.services.LoanService;
import com.librarymanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
        LocalDateTime loanDate = loanListNotReturned.get(0).getLoanDate();
        
        model.addAttribute("userId", userId);
        model.addAttribute("loanListNotReturned", loanListNotReturned);
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
