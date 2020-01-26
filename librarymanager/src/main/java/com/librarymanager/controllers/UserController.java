package com.librarymanager.controllers;

import com.librarymanager.entities.Book;
import com.librarymanager.entities.User;
import com.librarymanager.services.BookService;
import com.librarymanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id, Model model) {
        service.delete(id);
        return "redirect:/user/all";
    }

    @RequestMapping("/edit/{id}")
    public String editBook(@PathVariable long id, Model model) {
        User user = service.get(id);
        model.addAttribute("user", user);
        return "useredit";
    }

    @RequestMapping("/all")
    public String listUsers(@RequestParam(required = false, defaultValue = "") String email, Model model) {
        List<User> usersList;

        if(email.isEmpty()) {
            usersList = service.listAll();
        } else {
            usersList = service.filterByEmail(email);
        }

        model.addAttribute("usersList", usersList);
//        model.addAttribute("userId", getLoggedInUser().getId());
        return "userall";
    }

//    private User getLoggedInUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return (User) auth.getPrincipal();
//    }
}
