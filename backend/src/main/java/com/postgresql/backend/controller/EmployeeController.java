package com.postgresql.backend.controller;

import com.postgresql.backend.exception.EmployeeNotFoundException;
import com.postgresql.backend.model.Employee;
import com.postgresql.backend.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller in order to implement routing
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepo employeeRepo;

    // returns all the employees registered in the table
    @GetMapping("/getAll")
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }


    // add new employee
    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {

        employeeRepo.save(employee);
        return ResponseEntity.ok("Employee was added successfully");
    }

    // get employee based on its ID
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    // update fields of the employee using its ID
    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        employeeRepo.findById(id).map(employee -> {
            employee.setFullName(newEmployee.getFullName());
            employee.setEmail(newEmployee.getEmail());
            employee.setJobTitle(newEmployee.getJobTitle());
            employee.setAfm(newEmployee.getAfm());
            employee.setSalary(newEmployee.getSalary());
            employee.setPassword(newEmployee.getPassword());
            return employeeRepo.save(employee);
        }).orElseThrow(() -> new EmployeeNotFoundException(id));

        return ResponseEntity.ok("The information of the employee with id " + id + " have been successfully updated");

    }

    // delete an employee
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        if (!employeeRepo.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }

        employeeRepo.deleteById(id);
        return ResponseEntity.ok("Employee with id " + id + " has been deleted successfully");
    }
}
