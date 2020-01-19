package com.librarymanager.controllers;

import com.librarymanager.entities.Book;
import com.librarymanager.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/book")
public class BooksController {
    @Autowired
    private BookService service;

    @GetMapping("/testing")
    public String test(Model model) {
        //model.addAttribute("testObj", "Hello Spring!");
        return "allbooks";
    }

//    @GetMapping("/all")
//    public String listAllBooks(Model model) {
//        List<Book> listBooks = service.listAll();
//        model.addAttribute("listBooks", listBooks);
//        return "allbooks";
//    }

}
