package com.librarymanager.controllers;

import com.librarymanager.dto.BookCopyDto;
import com.librarymanager.dto.BookDto;
import com.librarymanager.entities.Book;
import com.librarymanager.entities.BookCopy;
import com.librarymanager.entities.User;
import com.librarymanager.services.BookCopyService;
import com.librarymanager.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bookcopy")
public class BookCopyController {
    @Autowired
    private BookCopyService bookCopyService;

    @Autowired
    private BookService bookService;

    @GetMapping
    public String listBookCopyByBookId(@RequestParam long bookId, Model model) {
        Optional<Book> book = bookService.get(bookId);
        if(book.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Cartea cu id-ul mentionat nu exista");
        }

        List<BookCopy> bookCopyList = bookCopyService.getAllBookCopiesByBookId(bookId);
        BookCopy bookCopy = new BookCopy();
        bookCopy.setBook(book.get());
        bookCopy.setId(bookId + "-");

        model.addAttribute("bookCopyList", bookCopyList);
        model.addAttribute("bookcopy", bookCopy);
        model.addAttribute("showForm", false);
        return "bookcopy/all";
    }

    @PostMapping
    public String addNewBookCopy(@ModelAttribute("bookcopy") @Valid BookCopyDto bookcopyDto,
                       BindingResult result,
                       @RequestParam long bookId, Model model) {
        Optional<Book> book = bookService.get(bookId);
        if(book.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Cartea cu id-ul mentionat nu exista");
        }
        bookcopyDto.setBook(book.get());

        if(!bookcopyDto.getId().startsWith(Long.toString(bookcopyDto.getBookId()) + "-")) {
            result.rejectValue("id", null, "Id-ul exemplarului trebuie sa inceapa cu <id_carte>-");
        }

        if(result.hasErrors()) {
            model.addAttribute("showForm", true);
            return "bookcopy/all";
        }

        bookCopyService.save(bookcopyDto);
        model.addAttribute("showForm", false);
        return "redirect:/bookcopy?bookId=" + bookId + "&addSuccess";
    }
}
