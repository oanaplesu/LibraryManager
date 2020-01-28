package com.librarymanager.services;

import com.librarymanager.entities.Loan;
import com.librarymanager.entities.Role;
import com.librarymanager.repositories.LoanRepository;
import com.librarymanager.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository repo;

    public List<Role> listAll() {
        return repo.findAll();
    }

    public void save(Role role) {
        repo.save(role);
    }

    public Role get(long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) {
        repo.deleteById(id);
    }

    public Role findByName(String name) {
        return repo.findByName(name);
    }
}
