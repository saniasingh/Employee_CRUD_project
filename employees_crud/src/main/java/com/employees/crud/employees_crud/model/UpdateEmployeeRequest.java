package com.employees.crud.employees_crud.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEmployeeRequest implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
