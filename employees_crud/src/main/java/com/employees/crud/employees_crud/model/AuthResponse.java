package com.employees.crud.employees_crud.model;

import com.employees.crud.employees_crud.domain.User;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse implements Serializable {
    private User user;
    private String token;
}
