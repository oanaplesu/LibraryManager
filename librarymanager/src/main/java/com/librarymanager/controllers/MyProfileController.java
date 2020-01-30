package com.librarymanager.controllers;

import com.librarymanager.dto.UserEditDto;
import com.librarymanager.entities.Loan;
import com.librarymanager.entities.Role;
import com.librarymanager.entities.User;
import com.librarymanager.misc.NotReturnedStruct;
import com.librarymanager.services.LoanService;
import com.librarymanager.services.RoleService;
import com.librarymanager.services.UserService;
import org.apache.commons.lang3.tuple.Pair;
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
@RequestMapping("/myprofile")
public class MyProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LoanService loanService;

    @GetMapping
    public String showMyProfile(Model model) {
        User user = getLoggedInUser();

        model.addAttribute("user", user );
        return "user/myprofile";
    }

    @RequestMapping("/edit/")
    public String editMyProfile(Model model) {
        User user = getLoggedInUser();
        model.addAttribute("redirectMyProfile", true);
        model.addAttribute("user", user);
        model.addAttribute("rolesStr", getRolesStringList(user.getRoles()));
        return "user/edit";
    }

    @PostMapping("/edit/")
    public String saveEditedMyProfile(@ModelAttribute("user") @Valid UserEditDto userDto,
                                 BindingResult result,
                                 @RequestParam(required = false, defaultValue = "ROLE_USER") String role,
                                 Model model) {
        User user = getLoggedInUser();
        model.addAttribute("rolesStr", getRolesStringList(user.getRoles()));
        model.addAttribute("redirectMyProfile", true);

        if (result.hasErrors()) {
            return "user/edit";
        }

        userService.saveEditedUser(user, userDto, role);
        return "redirect:/myprofile/edit/?editSuccess";
    }

    @GetMapping("/loans")
    public String showLoans(Model model) {
        long userId = getLoggedInUser().getId();
        List<Loan> loanReturnedList = loanService.filterByUserIdReturned(userId);
        List<Pair<Loan, NotReturnedStruct>> loanNotReturnedList = loanService.filterByUserIdNotReturned(userId);

        model.addAttribute("userId", userId);
        model.addAttribute("loanNotReturnedList", loanNotReturnedList);
        model.addAttribute("loanReturnedList", loanReturnedList);
        return "loan/all";
    }

    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userService.findByEmail(currentPrincipalName);
    }

    private User getUserOrThrow404(Long id) {
        Optional<User> user = userService.get(id);
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
