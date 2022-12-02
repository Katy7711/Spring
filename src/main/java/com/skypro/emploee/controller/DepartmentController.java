package com.skypro.emploee.controller;

import com.skypro.emploee.model.Employee;
import com.skypro.emploee.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    public DepartmentService getDepartmentService() {
        return departmentService;
    }
    @PostMapping("/employees")
    public Collection<Employee> getDepartmentEmployees(int department){
        return this.departmentService.getEmployeesByDepartmentStream(department).toList();
        }
    @GetMapping("/{id}/salary/sum")
    public int getSalarySum (@PathParam ("id") int department) {
        return this.departmentService.getSumSalariesByDepartment(department);
    }
    @GetMapping ("/{id}/salary/max")
    public int getEmployeeWithMinSalary(@PathParam ("id") int department) {
        return this.departmentService.getMaxSalaryByDepartment(department);
    }
    @GetMapping ("/{id}/salary/min")
    public int getEmployeeWithMaxSalary(@PathParam ("id") int department) {
        return this.departmentService.getMinSalaryByDepartment(department);
    }
    @GetMapping ("/employees")
    Map<Integer, List<Employee>> getEmployeesGroupedByDepartment() {
        return this.departmentService.getEmployeesGroupedByDepartment();
    }
}









