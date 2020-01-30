package com.librarymanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
        return "redirect:/book/all";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "user/login";
    }
}