package com.librarymanager.services;

import com.librarymanager.dto.UserEditDto;
import com.librarymanager.entities.User;
import com.librarymanager.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface UserService extends UserDetailsService {
    User findByEmail(String email);

    public List<User> listAll();

    public void save(UserRegistrationDto registration);

    public void saveEditedUser(User user, UserEditDto registration, String role);

    public Optional<User> get(long id);

    public void delete(long id);

    public List<User> filterByEmail(String email);

}
