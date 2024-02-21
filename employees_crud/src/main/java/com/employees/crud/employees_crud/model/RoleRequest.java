package com.employees.crud.employees_crud.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequest implements Serializable {
    private String name;
}

