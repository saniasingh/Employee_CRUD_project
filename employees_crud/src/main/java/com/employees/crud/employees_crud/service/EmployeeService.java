package com.employees.crud.employees_crud.service;

import com.employees.crud.employees_crud.domain.Employee;
import com.employees.crud.employees_crud.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Employee> getAllEmployees() throws RuntimeException {
        try {
            return employeeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Employee getEmployeeById(Integer id) throws RuntimeException {
        try {
            final Optional<Employee> employee = employeeRepository.findById(id);
            return employee.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Employee updateEmployee(Employee employee) throws RuntimeException {
        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Employee addEmployee(Employee employee) throws RuntimeException {
        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEmployee(Integer id) throws RuntimeException {
        try {
            employeeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employee> getSortedEmployees(String order) {
        if ("asc".equalsIgnoreCase(order)) {
            return employeeRepository.findAllByOrderByFirstNameAsc();
        } else if ("desc".equalsIgnoreCase(order)) {
            return employeeRepository.findAllByOrderByFirstNameDesc();
        } else {
            throw new IllegalArgumentException("Invalid order parameter. Use 'asc' or 'desc'.");
        }
    }

    public List<Employee> searchEmployeesByFirstName(String firstName) {
        return employeeRepository.findByFirstNameContainingIgnoreCase(firstName);
    }
}
