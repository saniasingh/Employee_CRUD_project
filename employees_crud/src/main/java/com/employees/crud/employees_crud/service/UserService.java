package com.employees.crud.employees_crud.service;


import com.employees.crud.employees_crud.domain.Role;
import com.employees.crud.employees_crud.domain.User;
import com.employees.crud.employees_crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User registerUser(String username, String password, Set<Role> roles) throws RuntimeException {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRoles(roles);

            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
