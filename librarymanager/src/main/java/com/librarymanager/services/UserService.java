package com.librarymanager.services;

import com.librarymanager.entities.User;
import com.librarymanager.misc.UserRegistrationDto;
import com.librarymanager.repositories.BookRepository;
import com.librarymanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface UserService extends UserDetailsService {
    User findByEmail(String email);

    User save(UserRegistrationDto registration);

    public List<User> listAll();

    public void save(User book);

    public User get(long id);

    public void delete(long id);

    public List<User> filterByEmail(String email);

}
