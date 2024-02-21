package com.employees.crud.employees_crud.controller;

import com.employees.crud.employees_crud.domain.Employee;
import com.employees.crud.employees_crud.model.AddEmployeeRequest;
import com.employees.crud.employees_crud.model.ApiResponse;
import com.employees.crud.employees_crud.model.UpdateEmployeeRequest;
import com.employees.crud.employees_crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees() {
        try {
            final ApiResponse<List<Employee>> apiResponse = ApiResponse.success("Fetched employee list successfully", employeeService.getAllEmployees(), 200);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        } catch (Exception e) {
            final ApiResponse<List<Employee>> apiResponse = ApiResponse.error("Something went wrong!!", 500);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<ApiResponse<Employee>> addEmployee(@RequestBody AddEmployeeRequest addEmployeeRequest) {
        try {
            if (addEmployeeRequest.getFirstName() == null) {
                final ApiResponse<Employee> apiResponse = ApiResponse.error("firstName is required", 500);
                return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
            }
            if (addEmployeeRequest.getLastName() == null) {
                final ApiResponse<Employee> apiResponse = ApiResponse.error("lastName is required", 500);
                return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
            }
            if (addEmployeeRequest.getEmail() == null) {
                final ApiResponse<Employee> apiResponse = ApiResponse.error("email is required", 500);
                return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
            }
            final Employee employee = Employee.builder().firstName(addEmployeeRequest.getFirstName()).lastName(addEmployeeRequest.getLastName()).email(addEmployeeRequest.getEmail()).build();
            final Employee addedEmployee = employeeService.addEmployee(employee);
            final ApiResponse<Employee> apiResponse = ApiResponse.success("Added employee successfully!!", employeeService.updateEmployee(addedEmployee), 200);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        } catch (RuntimeException e) {
            final ApiResponse<Employee> apiResponse = ApiResponse.error("Something went wrong!!", 500);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        }

    }

    @GetMapping("/{employeeId}")
    @ResponseBody
    public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@PathVariable Integer employeeId) {
        try {

            final ApiResponse<Employee> apiResponse = ApiResponse.success("Fetched employee list successfully", employeeService.getEmployeeById(employeeId), 200);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        } catch (RuntimeException e) {
            final ApiResponse<Employee> apiResponse = ApiResponse.error("Something went wrong!!", 500);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        }
    }


    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(@RequestBody UpdateEmployeeRequest updateEmployeeRequest) {
        try {
            if (updateEmployeeRequest.getId() == null) {
                final ApiResponse<Employee> apiResponse = ApiResponse.error("id is required", 500);
                return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
            }
            if (updateEmployeeRequest.getFirstName() == null) {
                final ApiResponse<Employee> apiResponse = ApiResponse.error("firstName is required", 500);
                return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
            }
            if (updateEmployeeRequest.getLastName() == null) {
                final ApiResponse<Employee> apiResponse = ApiResponse.error("lastName is required", 500);
                return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
            }
            if (updateEmployeeRequest.getEmail() == null) {
                final ApiResponse<Employee> apiResponse = ApiResponse.error("email is required", 500);
                return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
            }
            final Employee employee = employeeService.getEmployeeById(updateEmployeeRequest.getId());
            if (employee == null) {
                final ApiResponse<Employee> apiResponse = ApiResponse.error("No employee exists with id" + updateEmployeeRequest.getId().toString(), 500);
                return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
            }
            final Employee newEmployee = Employee.builder().id(updateEmployeeRequest.getId()).firstName(updateEmployeeRequest.getFirstName()).lastName(updateEmployeeRequest.getLastName()).email(updateEmployeeRequest.getEmail()).build();
            final ApiResponse<Employee> apiResponse = ApiResponse.success("Updated employee successfully!!", employeeService.updateEmployee(newEmployee), 200);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        } catch (RuntimeException e) {
            final ApiResponse<Employee> apiResponse = ApiResponse.error("Something went wrong!!", 500);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        }

    }

    @DeleteMapping("/{employeeId}")
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable Integer employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
            final ApiResponse<String> apiResponse = ApiResponse.success("Deleted employee successfully", "Deleted employee id -" + employeeId, 200);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        } catch (RuntimeException e) {
            final ApiResponse<String> apiResponse = ApiResponse.error("Something went wrong!!", 500);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        }
    }

    @GetMapping("/sort")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<Employee>>> getSortedEmployees(@RequestParam String order) {
        try {
            List<Employee> sortedEmployees = employeeService.getSortedEmployees(order);
            final ApiResponse<List<Employee>> apiResponse = ApiResponse.success("Fetched employee list successfully", sortedEmployees, 200);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        } catch (RuntimeException e) {
            final ApiResponse<List<Employee>> apiResponse = ApiResponse.error("Something went wrong!!", 500);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        }
    }

    @GetMapping("/search/{query}")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<Employee>>> searchEmployeesByFirstName(@PathVariable String query) {
        try {
            List<Employee> searchResults = employeeService.searchEmployeesByFirstName(query);
            final ApiResponse<List<Employee>> apiResponse = ApiResponse.success("Fetched employee list successfully", searchResults, 200);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        } catch (RuntimeException e) {
            final ApiResponse<List<Employee>> apiResponse = ApiResponse.error("Something went wrong!!", 500);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        }
    }
}
