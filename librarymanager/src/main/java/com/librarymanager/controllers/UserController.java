package com.librarymanager.controllers;

import com.librarymanager.services.BookService;
import com.librarymanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/book")
public class UserController {
    @Autowired
    private UserService service;
}
