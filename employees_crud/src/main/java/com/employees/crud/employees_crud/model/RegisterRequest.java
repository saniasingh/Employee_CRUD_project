package com.employees.crud.employees_crud.model;


import com.employees.crud.employees_crud.domain.Role;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest implements Serializable {
    private String username;
    private String password;
    private Set<Role> roles;
}
