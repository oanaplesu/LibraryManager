package com.librarymanager.controllers;

import com.librarymanager.entities.Book;
import com.librarymanager.entities.Role;
import com.librarymanager.entities.User;
import com.librarymanager.services.BookService;
import com.librarymanager.services.RoleService;
import com.librarymanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private RoleService roleService;

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        service.delete(id);
        return "redirect:/user/all";
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

    @RequestMapping("/edit/{id}")
    public String editUser(@PathVariable long id, Model model) {
        User user = service.get(id);
        List<String> rolesStr = new ArrayList<>();

        for(Role role: user.getRoles()) {
            rolesStr.add(role.getName());
        }

        model.addAttribute("user", user);
        model.addAttribute("rolesStr", rolesStr);
        return "useredit";
    }

    @PostMapping("/save")
    public String saveUSer(@ModelAttribute("user") User user,
                           @RequestParam String role) {
        List<Role> roles = new ArrayList<>();
        Role roleUser = roleService.findByName("ROLE_USER");
        Role roleLibrarian = roleService.findByName("ROLE_LIBRARIAN");
        Role roleAdmin = roleService.findByName("ROLE_ADMIN");

        switch (role) {
            case "ROLE_ADMIN":
                roles.add(roleAdmin);
            case "ROLE_LIBRARIAN":
                roles.add(roleLibrarian);
            case "ROLE_USER":
                roles.add(roleUser);
            default:
                break;
        }

        user.setRoles(roles);

        User oldUser = service.get(user.getId());
        user.setPassword(oldUser.getPassword());
        service.save(user);

        return "redirect:/user/edit/" + user.getId();
    }

    @GetMapping("/myprofile")
    public String showMyProfile(Model model) {
        User user = getLoggedInUser();

        model.addAttribute("user", user );
        return "myprofile";
    }

    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return service.findByEmail(currentPrincipalName);
    }
}
