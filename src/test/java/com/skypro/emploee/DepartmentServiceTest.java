package com.skypro.emploee;

import com.skypro.emploee.exception.EmployeeNotFoundException;
import com.skypro.emploee.model.Employee;
import com.skypro.emploee.record.EmployeeRequest;
import com.skypro.emploee.service.DepartmentService;
import com.skypro.emploee.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    private final List<Employee> employees = List.of(
            new Employee("Тест", "Тест", 1, 10000),
            new Employee("ТестO", "ТестO", 2, 11000),
            new Employee("ТестA", "ТестA", 3, 12000),
            new Employee("ТестB", "ТестB", 4, 13000),
            new Employee("ТестC", "ТестC", 1, 14000),
            new Employee("ТестD", "ТестD", 2, 15000),
            new Employee("ТестF", "ТестF", 3, 16000),
            new Employee("ТестG", "ТестG", 4, 17000));
    @Mock
    EmployeeService employeeService;
    @InjectMocks
    DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        when(employeeService.getAllEmployees()).thenReturn(employees);
    }

    @Test
    void getEmployeesByDepartment() {
        Collection<Employee> employeesByDepartment = this.departmentService.getDepartmentEmployees(2);
        Assertions.assertThat(employees).hasSize(2).contains(employees.get(1), (employees.get(5)));
    }

    @Test
    void getSumSalariesByDepartment() {
        int sum = this.departmentService.getSumSalariesByDepartment(1);
        Assertions.assertThat(sum).isEqualTo(24000);
    }

    @Test
    void getMaxSumSalariesByDepartment() {
        int sum = this.departmentService.getMaxSalaryByDepartment(1);
        Assertions.assertThat(sum).isEqualTo(14000);
    }

    @Test
    void getMinSumSalariesByDepartment() {
        int sum = this.departmentService.getMinSalaryByDepartment(3);
        Assertions.assertThat(sum).isEqualTo(12000);
    }

    @Test
    void getEmployeesGroupByDepartment() {
        Map<Integer, List<Employee>> employeesGroup = this.departmentService.getEmployeesGroupedByDepartment();
        Assertions.assertThat(employeesGroup).hasSize(4)
                .containsEntry(1, List.of(employees.get(0), employees.get(4)))
                .containsEntry(2, List.of(employees.get(1), employees.get(5)))
                .containsEntry(3, List.of(employees.get(2), employees.get(6)))
                .containsEntry(4, List.of(employees.get(3), employees.get(7)));
    }

    @Test
    void whenNoEmployeesByDepartmentThanGroupByReturnEmptyMap() {
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        Map<Integer, List<Employee>> employeesGroup = this.departmentService.getEmployeesGroupedByDepartment();
        Assertions.assertThat(employeesGroup).isEmpty();
    }

    @Test
    void whenNoEmployeesThanMinSalaryInDepartmentException() {
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        Assertions.assertThatThrownBy(() -> departmentService.getMinSalaryByDepartment(0))
                .isInstanceOf(EmployeeNotFoundException.class);
    }

    @Test
    void whenNoEmployeesThanMaxSalaryInDepartmentException() {
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        Assertions.assertThatThrownBy(() -> departmentService.getMaxSalaryByDepartment(0))
                .isInstanceOf(EmployeeNotFoundException.class);
    }
}
