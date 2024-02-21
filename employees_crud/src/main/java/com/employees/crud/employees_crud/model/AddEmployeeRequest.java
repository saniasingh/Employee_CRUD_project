package com.employees.crud.employees_crud.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddEmployeeRequest implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
}
