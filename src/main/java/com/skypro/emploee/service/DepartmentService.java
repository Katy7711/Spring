package com.skypro.emploee.service;

import com.skypro.emploee.exception.EmployeeNotFoundException;
import com.skypro.emploee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Stream<Employee> getEmployeesByDepartmentStream(int department) {
        return employeeService.getAllEmployees().stream().filter(e -> e.getDepartment() == department);
    }

    public Collection<Employee> getDepartmentEmployees(int department) {
        return getEmployeesByDepartmentStream(department).collect(Collectors.toList());
    }

    public int getSumSalariesByDepartment(int department) {
        return getEmployeesByDepartmentStream(department).mapToInt(Employee::getSalary).sum();
    }

    public int getMaxSalaryByDepartment(int department) {
        return getEmployeesByDepartmentStream(department).mapToInt(Employee::getSalary).max().orElseThrow(EmployeeNotFoundException::new);
    }

    public int getMinSalaryByDepartment(int department) {
        return getEmployeesByDepartmentStream(department).mapToInt(Employee::getSalary).min().orElseThrow(EmployeeNotFoundException::new);
    }

    public Map<Integer, List<Employee>> getEmployeesGroupedByDepartment() {
        return employeeService.getAllEmployees().stream().collect(Collectors.groupingBy(Employee::getDepartment));
    }
}

