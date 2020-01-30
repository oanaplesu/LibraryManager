package com.librarymanager.services;

import java.util.*;
import java.util.stream.Collectors;

import com.librarymanager.dto.UserEditDto;
import com.librarymanager.entities.Role;
import com.librarymanager.entities.User;
import com.librarymanager.dto.UserRegistrationDto;
import com.librarymanager.repositories.RoleRepository;
import com.librarymanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public void save(UserRegistrationDto registration) {
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPhone(registration.getPhone());
        user.setAddress(registration.getAddress());
        if(!registration.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(registration.getPassword()));
        }
        user.setRoles(getRolesList("ROLE_USER"));
        repo.save(user);
    }

    public void saveEditedUser(User user, UserEditDto registration, String role) {
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPhone(registration.getPhone());
        user.setAddress(registration.getAddress());
        user.setRoles(getRolesList(role));
        repo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Email sau parola invalida");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection < ? extends GrantedAuthority > mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    public List<User> listAll() {
        return repo.findAll();
    }

    public List<User> filterByEmail(String email) {
        return repo.findByEmailContaining(email);
    }

    public void save(User book) {
        repo.save(book);
    }

    public Optional<User> get(long id) {
        return repo.findById(id);
    }

    public void delete(long id) {
        repo.deleteById(id);
    }

    private List<Role> getRolesList(String role)
    {
        List<Role> roles = new ArrayList<>();
        Role roleUser = roleRepo.findByName("ROLE_USER");
        Role roleLibrarian = roleRepo.findByName("ROLE_LIBRARIAN");
        Role roleAdmin = roleRepo.findByName("ROLE_ADMIN");

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

        return roles;
    }
}
