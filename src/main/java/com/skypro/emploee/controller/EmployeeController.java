package com.skypro.emploee.controller;

import com.skypro.emploee.exception.InvalidEmployeeRequestException;
import com.skypro.emploee.model.Employee;
import com.skypro.emploee.record.EmployeeRequest;
import com.skypro.emploee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController (EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping ("/employees")
    public Collection<Employee> getAllEmployee (){
        return this.employeeService.getAllEmployees();
    }

    @PostMapping ("/employees")
    public Object createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        try {
            return ResponseEntity.ok(this.employeeService.addEmployee(employeeRequest));
        } catch (InvalidEmployeeRequestException e) {
            System.out.println("e");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping ("/employees/salary/sum")
    public int getSalarySum() {
        return this.employeeService.getSalarySum();
    }
    @GetMapping ("/employees/salary/min")
    public Employee getEmployeeWithMinSalary() {
        return this.employeeService.getEmployeeWithMinSalary();
    }
    @GetMapping ("/employees/salary/max")
    public Employee getEmployeeWithMaxSalary() {
        return this.employeeService.getEmployeeWithMaxSalary();
    }
    @GetMapping ("/employees/salary/highSalary")
    public List<Employee> getEmployeeWithHighSalary() {
        return this.employeeService.getEmployeeWithHighSalary();
    }
}
