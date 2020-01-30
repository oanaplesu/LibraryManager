package com.librarymanager.controllers;

import com.librarymanager.dto.BookDto;
import com.librarymanager.entities.Book;
import com.librarymanager.misc.BookCopyStats;
import com.librarymanager.services.BookCopyService;
import com.librarymanager.services.BookService;
import com.librarymanager.services.StorageService;
import com.librarymanager.storage.StorageException;
import com.librarymanager.storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/book")
public class BooksController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookCopyService bookCopyService;

    @Autowired
    private StorageService storageService;


    @GetMapping(value = {"/all"})
    public String listAllBooks(@RequestParam(required = false, defaultValue = "1") int page,
                               @RequestParam(required = false) String title,
                               @RequestParam(required = false) String author,
                               @RequestParam(required = false) String year,
                               Model model) {
        Page<Book> booksPage = bookService.filterAndPaginate(page, title, author, year);

        model.addAttribute("booksList", booksPage.getContent());
        model.addAttribute("page", page);
        model.addAttribute("isFirstPage", booksPage.isFirst());
        model.addAttribute("isLastPage", booksPage.isLast());
        return "book/all";
    }

    @GetMapping("/new")
    public String showNewBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "book/new";
    }

    @PostMapping("/new")
    public String saveNewBook(@ModelAttribute("book") @Valid BookDto bookDto,
                              BindingResult result,
                             @RequestParam(required = false) MultipartFile file) {
        if(result.hasErrors()) {
            return "book/new";
        }

        try {
            String imagePath = "";

            if(!file.isEmpty()) {
                storageService.store(file);
                imagePath = file.getOriginalFilename();
            }

            bookService.save(bookDto, imagePath);
        } catch (StorageException e) {
            result.reject("file", "Incarcati o imagine valida");
            return "book/new";
        }

        return "redirect:/book/all?addSuccess";
    }

    @RequestMapping("/edit/{id}")
    public String showEditBook(@PathVariable long id, Model model) {
        Book book = getBookOrThrow404(id);
        model.addAttribute("book", book );
        return "book/edit";
    }

    @PostMapping("/edit/{id}")
    public String saveEditBook(@ModelAttribute("book") @Valid BookDto bookDto,
                               BindingResult result,
                               @PathVariable Long id,
                               @RequestParam(required = false) MultipartFile file) {
        Book book = getBookOrThrow404(id);

        if(result.hasErrors()) {
            return "book/edit";
        }

        try {
            String imagePath = "";

            if(!file.isEmpty()) {
                storageService.store(file);
                imagePath = file.getOriginalFilename();
            }

            bookService.saveEditedBook(book, bookDto, imagePath);
        } catch (StorageException e) {
            result.reject("file", "Incarcati o imagine valida");
            return "redirect:/book/edit" + id;
        }

        return "redirect:/book/all?editSuccess";
    }

    @GetMapping("/view/{id}")
    public String showBookDetails(@PathVariable long id, Model model) {
        Book book = getBookOrThrow404(id);
        model.addAttribute("book", book);

        List<BookCopyStats> bookCopyStats = bookCopyService.getBookCopyStatsForBookId(id);
        model.addAttribute("stats", bookCopyStats);
        return "book/details";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable long id, Model model) {
        getBookOrThrow404(id);
        bookService.delete(id);
        return "redirect:/book/all?deleteSuccess";
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

    private Book getBookOrThrow404(Long id) {
        Optional<Book> book = bookService.get(id);
        if(book.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nu s-a gasit resursa ceruta");
        }
        return book.get();
    }
}
