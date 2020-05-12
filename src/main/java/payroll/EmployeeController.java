package payroll;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class EmployeeController {
    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    List<Employee> viewAllEmployees() {
        return repository.findAll();
    }

    @GetMapping("/employees/{id}")
    Employee getEmployeeByID(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    // @GetMapping("/employees/{name}")
    // Employee getEmployeeByName(@PathVariable String varname){
    // return repository.findAll(employee -> employee.name.equals(varname));
    // }

    @PostMapping("/employees/add")
    Employee addEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    @PutMapping("/employees/update/{id}")
    Employee updateEmployeeRole(@RequestBody Employee newEmp, @PathVariable Long id) {
        return repository.findById(id).map(employee -> {
            employee.setRole(newEmp.getRole());
            employee.setName(newEmp.getName());
            return repository.save(employee);
        }).orElseGet(() -> {
            newEmp.setId(id);
            return repository.save(newEmp);
        });
    }

    @DeleteMapping("employees/delete/{id}")
    String deleteEmployeeById(@PathVariable Long id) {
        repository.deleteById(id);
        return "Employee Deleted Successfully";
    }
}