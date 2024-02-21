package com.employees.crud.employees_crud.service;

import com.employees.crud.employees_crud.domain.Role;
import com.employees.crud.employees_crud.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getRoleByName(String roleName) {
        try {
            return roleRepository.findByName(roleName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Role addRole(Role role) throws RuntimeException {
        try {
            return roleRepository.save(role);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Role> getAllRoles() throws RuntimeException {
        try {
            return roleRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
