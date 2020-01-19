package com.librarymanager.controllers;

import com.librarymanager.entities.Book;
import com.librarymanager.services.BookService;
import com.librarymanager.services.StorageService;
import com.librarymanager.storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "/book")
public class BooksController {
    @Autowired
    private BookService service;

    @Autowired
    private StorageService storageService;

    @GetMapping("/all")
    public String listAllBooks(Model model) {
        List<Book> booksList = service.listAll();
        model.addAttribute("booksList", booksList);
        return "allbooks";
    }

    @GetMapping("/new")
    public String addNewBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "newbook";
    }

    @PostMapping("/new")
    public String addNewBook(@ModelAttribute("book") Book book, @RequestParam("file") MultipartFile file) {
        book.setImagePath(file.getOriginalFilename());
        service.save(book);
        String imageName = Long.toString(book.getId());
        storageService.store(file);
        return "redirect:/book/all";
    }

    @GetMapping("/{id}")
    public String showBookDetails(@PathVariable String id, Model model) {

        return "bookdetails";
    }

    @GetMapping("/{id}/delete")
    public String deleteBook(@PathVariable String id, Model model) {

        return "bookdetails";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable String id, Model model) {

        return "bookedit";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

      //  storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
