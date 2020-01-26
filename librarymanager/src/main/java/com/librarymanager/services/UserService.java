package com.librarymanager.services;

import com.librarymanager.entities.User;
import com.librarymanager.misc.UserRegistrationDto;
import com.librarymanager.repositories.BookRepository;
import com.librarymanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public interface UserService extends UserDetailsService {
    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}
