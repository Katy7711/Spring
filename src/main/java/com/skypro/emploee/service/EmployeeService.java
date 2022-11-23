package com.skypro.emploee.service;

import com.skypro.emploee.exception.EmployeeNotFoundException;
import com.skypro.emploee.model.Employee;
import com.skypro.emploee.record.EmployeeRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Map<Integer, Employee> employees = new HashMap<>();

    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }
    public Employee addEmployee(EmployeeRequest employeeRequest) {
        if (employeeRequest.getFirstName() == null || employeeRequest.getLastName() == null) {
            throw new IllegalArgumentException("Employee should be set");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getDepartment(),
                employeeRequest.getSalary());
        this.employees.put(employee.getId(),employee);
        return employee;

        }

    public int getSalarySum() {
        return employees.values().stream()
                .mapToInt(Employee:: getSalary)
                .sum();
    }

    public Employee getEmployeeWithMaxSalary() {
        return employees.values().stream().max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public Employee getEmployeeWithMinSalary() {
        return employees.values().stream().min(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
        }

    public List<Employee> getEmployeeWithHighSalary() {
       Double averageSalary = getAverageSalary();
       if (averageSalary == null) {
           return Collections.emptyList();
       }
       return employees.values().stream().filter(e->e.getSalary() > averageSalary).collect(Collectors.toList());
    }

    private Double getAverageSalary (){
        return employees.values().stream().collect(Collectors.averagingInt(Employee::getSalary));
    }
}



