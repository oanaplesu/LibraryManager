package com.librarymanager.controllers;

import com.librarymanager.dto.UserEditDto;
import com.librarymanager.dto.UserRegistrationDto;
import com.librarymanager.entities.Role;
import com.librarymanager.entities.User;
import com.librarymanager.services.RoleService;
import com.librarymanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private RoleService roleService;

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        getUserOrThrow404(id);
        service.delete(id);
        return "redirect:/user/all?deleteSuccess";
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
        return "user/all";
    }

    @RequestMapping("/edit/{id}")
    public String editUser(@PathVariable long id, Model model) {
        User user = getUserOrThrow404(id);
        model.addAttribute("user", user);
        model.addAttribute("rolesStr", getRolesStringList(user.getRoles()));
        model.addAttribute("redirectMyProfile", false);
        return "user/edit";
    }

    @PostMapping("/edit/{id}")
    public String saveEditedUSer(@ModelAttribute("user") @Valid UserEditDto userDto,
                                 BindingResult result,
                                 @PathVariable Long id,
                                 @RequestParam(required = false, defaultValue = "ROLE_USER") String role,
                                 Model model) {
        User user = getUserOrThrow404(id);
        model.addAttribute("rolesStr", getRolesStringList(user.getRoles()));
        model.addAttribute("redirectMyProfile", false);

        if (result.hasErrors()) {
            return "user/edit";
        }

        service.saveEditedUser(user, userDto, role);
        return "redirect:/user/edit/" + id + "?editSuccess";
    }

    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return service.findByEmail(currentPrincipalName);
    }

    private User getUserOrThrow404(Long id) {
        Optional<User> user = service.get(id);
        if(user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nu s-a gasit resursa ceruta");
        }
        return user.get();
    }

    private List<String> getRolesStringList(Collection<Role> list) {
        List<String> rolesStr = new ArrayList<>();

        for(Role role: list) {
            rolesStr.add(role.getName());
        }
        return rolesStr;
    }
}
