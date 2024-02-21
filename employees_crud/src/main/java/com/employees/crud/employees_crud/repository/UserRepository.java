package com.employees.crud.employees_crud.repository;

import com.employees.crud.employees_crud.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
