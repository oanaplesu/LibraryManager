package com.librarymanager.services;

import com.librarymanager.repositories.BookRepository;
import com.librarymanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository repo;
}
