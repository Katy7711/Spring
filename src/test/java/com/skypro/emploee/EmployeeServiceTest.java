package com.skypro.emploee;

import com.skypro.emploee.exception.EmployeeNotFoundException;
import com.skypro.emploee.model.Employee;
import com.skypro.emploee.record.EmployeeRequest;
import com.skypro.emploee.service.EmployeeService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collection;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        this.employeeService = new EmployeeService();
        Stream.of(new EmployeeRequest("Тест", "Тест", 1, 10000),
                new EmployeeRequest("ТестO", "ТестO", 2, 11000),
                new EmployeeRequest("ТестA", "ТестA", 3, 12000),
                new EmployeeRequest("ТестB", "ТестB", 4, 13000),
                new EmployeeRequest("ТестC", "ТестC", 1, 14000),
                new EmployeeRequest("ТестD", "ТестD", 2, 15000),
                new EmployeeRequest("ТестF", "ТестF", 3, 16000),
                new EmployeeRequest("ТестG", "ТестG", 4, 17000)).forEach(employeeService::addEmployee);
    }

    @Test
    public void addEmployee() throws InvalidPropertiesFormatException {
        EmployeeRequest request = new EmployeeRequest("Иван", "Иванов", 4, 20000);
        Employee result = employeeService.addEmployee(request);
        assertEquals(request.getLastName(), result.getLastName());
        assertEquals(request.getFirstName(), result.getFirstName());
        assertEquals(request.getDepartment(), result.getDepartment());
        assertEquals(request.getSalary(), result.getSalary());

        Assertions.assertThat(employeeService.getAllEmployees()).contains(result);
    }

    @Test
    public void listEmployees() {
        Collection<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertThat(employees).hasSize(8);
        Assertions.assertThat(employees).first().extracting(Employee::getFirstName).isEqualTo("Тест");
    }

    public void sumOfSalaries() {
        int sum = employeeService.getSalarySum();
        Assertions.assertThat(sum).isEqualTo(108000);
    }

    @Test
    public void getEmployeeWithMaxSalary() {
        Employee employee = employeeService.getEmployeeWithMaxSalary();
        Assertions.assertThat(employee).isNotNull().extracting(Employee::getFirstName).isEqualTo("ТестG");
    }

    @Test
    public void getEmployeeWithMinSalary() {
        Employee employee = employeeService.getEmployeeWithMinSalary();
        Assertions.assertThat(employee).isNotNull().extracting(Employee::getFirstName).isEqualTo("Тест");
    }

    @Test
    public void getEmployeesWithSalaryMoreThanAverage() {
        List<Employee> employees = employeeService.getEmployeesWithSalaryMoreThanAverage();
        Assertions.assertThat(employees).hasSize(5)
                .contains(employees.get(3), employees.get(4), employees.get(5), employees.get(6), employees.get(7));
    }

    @Test
    public void removeEmployee() {
        employeeService.removeEmployee(employeeService.getAllEmployees().iterator().next().getId());
        Collection<Employee> employee = employeeService.getAllEmployees();
        Assertions.assertThat(employee).hasSize(7);
    }

    @Test
    public void ifNoEmployeesThanMinSalaryException() {
        EmployeeService employeeService = new EmployeeService();
        Assertions.assertThatThrownBy(employeeService::getEmployeeWithMinSalary)
                .isInstanceOf(EmployeeNotFoundException.class);
    }

    @Test
    public void ifNoEmployeesThanMaxSalaryException() {
        EmployeeService employeeService = new EmployeeService();
        Assertions.assertThatThrownBy(employeeService::getEmployeeWithMaxSalary)
                .isInstanceOf(EmployeeNotFoundException.class);
    }
}

