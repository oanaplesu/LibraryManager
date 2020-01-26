package com.librarymanager.controllers;

import com.librarymanager.entities.Book;
import com.librarymanager.entities.BookCopy;
import com.librarymanager.services.BookCopyService;
import com.librarymanager.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookcopy")
public class BookCopyController {
    @Autowired
    private BookCopyService bookCopyService;

    @Autowired
    private BookService bookService;

    @GetMapping
    public String listBookCopyByBookId(@RequestParam long bookId, Model model) {
        List<BookCopy> bookCopyList = bookCopyService.getAllBookCopiesByBookId(bookId);
        BookCopy bookCopy = new BookCopy();
        bookCopy.setId(bookId + "-");

        model.addAttribute("bookCopyList", bookCopyList);
        model.addAttribute("bookId", bookId);
        model.addAttribute("bookcopy", bookCopy);
        return "bookcopyall";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("bookcopy") BookCopy bookcopy,
                       @RequestParam long bookId) {
        Book book = bookService.get(bookId);
        bookcopy.setBook(book);
        bookCopyService.save(bookcopy);
        return "redirect:/bookcopy?bookId=" + Long.toString(bookId);
    }
}
