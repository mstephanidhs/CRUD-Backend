package com.postgresql.backend.controller;

import com.postgresql.backend.exception.EmployeeNotFoundException;
import com.postgresql.backend.model.Employee;
import com.postgresql.backend.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/sign")
public class AuthController {

    @Autowired
    EmployeeRepo employeeRepo;

    @PostMapping("/authenticate")
    public Employee getEmployee(@RequestBody Employee employee) {
        return employeeRepo.findByEmailAndPassword(employee.getEmail(), employee.getPassword()).orElseThrow(() -> new EmployeeNotFoundException(employee.getEmail()));

    }
}
