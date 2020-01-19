package com.librarymanager.controllers;

import com.librarymanager.entities.Book;
import com.librarymanager.misc.BookSpecification;
import com.librarymanager.misc.SearchCriteria;
import com.librarymanager.services.BookService;
import com.librarymanager.services.StorageService;
import com.librarymanager.storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/book")
public class BooksController {
    @Autowired
    private BookService bookService;

    @Autowired
    private StorageService storageService;


    @GetMapping(value = {"/page", "/page/{pageNr}"})
    public String listAllBooks(@PathVariable Optional<Long> pageNr,
                               @RequestParam(required = false) String title,
                               @RequestParam(required = false) String author,
                               @RequestParam(required = false) String year,
                               Model model) {
        long pageNr_ = pageNr.isPresent() ? pageNr.get() : 1;

        List<BookSpecification> filters = new ArrayList<>();

        if(title != null) {
            filters.add(new BookSpecification(new SearchCriteria("title", ":", title)));
        }

        if(author != null) {
            filters.add(new BookSpecification(new SearchCriteria("author", ":", author)));
        }

        if(year != null) {
            filters.add(new BookSpecification(new SearchCriteria("year", ":", year)));
        }

        Specification<Book> spec = null;

        for (BookSpecification filter: filters) {
            spec = ((spec == null) ? Specification.where(filter) : spec.and(filter));
        }

        List<Book> booksList;

        if(spec != null) {
            booksList = bookService.filterAll(spec);
        } else {
            booksList = bookService.listAll();
        }

        model.addAttribute("booksList", booksList);
        return "allbooks";
    }

    @GetMapping("/new")
    public String addNewBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "newbook";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") Book book,
                         @RequestParam(required = false, defaultValue = "false") boolean keepImage,
                         @RequestParam(required = false) MultipartFile file) {
        if(keepImage) {
            String oldImagePath = bookService.get(book.getId()).getImagePath();
            book.setImagePath(oldImagePath);
        } else if(!file.isEmpty()) {
            storageService.store(file);
            book.setImagePath(file.getOriginalFilename());
        }

        bookService.save(book);
        return "redirect:/book/page";
    }

    @GetMapping("/view/{id}")
    public String showBookDetails(@PathVariable long id, Model model) {
        Book book = bookService.get(id);
        model.addAttribute("book", book);
        return "bookdetails";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable long id, Model model) {
        bookService.delete(id);
        return "redirect:/book/page";
    }

    @RequestMapping("/edit/{id}")
    public String editBook(@PathVariable long id, Model model) {
        Book book = bookService.get(id);
        model.addAttribute("book", book);
        return "bookedit";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
