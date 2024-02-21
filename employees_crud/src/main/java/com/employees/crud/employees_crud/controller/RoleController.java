package com.employees.crud.employees_crud.controller;

import com.employees.crud.employees_crud.domain.Role;
import com.employees.crud.employees_crud.model.ApiResponse;
import com.employees.crud.employees_crud.model.RoleRequest;
import com.employees.crud.employees_crud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<ApiResponse<Role>> addRole(@RequestBody RoleRequest roleRequest) {
        try {
            if (roleService.getRoleByName(roleRequest.getName()) != null) {
                final ApiResponse<Role> apiResponse = ApiResponse.error("Role already exists", 500);
                return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
            }
            final Role newRole = Role.builder().name(roleRequest.getName()).build();
            final ApiResponse<Role> apiResponse = ApiResponse.success("Saved role successfully!!", roleService.addRole(newRole), 200);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        } catch (RuntimeException e) {
            final ApiResponse<Role> apiResponse = ApiResponse.error("Couldn't save role!!", 500);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        }
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<Role>>> addRole() {
        try {
            final ApiResponse<List<Role>> apiResponse = ApiResponse.success("Saved role successfully!!", roleService.getAllRoles(), 200);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        } catch (RuntimeException e) {
            final ApiResponse<List<Role>> apiResponse = ApiResponse.error("Something went wrong!!", 500);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        }
    }
}
