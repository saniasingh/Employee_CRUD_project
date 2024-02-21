package com.employees.crud.employees_crud.repository;

import com.employees.crud.employees_crud.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
