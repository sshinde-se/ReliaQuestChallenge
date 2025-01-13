package com.reliaquest.api.controller;

import com.reliaquest.api.entity.Employee;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.reliaquest.api.service.IEmployeeService;

import java.util.List;

import static com.reliaquest.api.dataprovider.EmployeeDataProvider.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class IEmployeeControllerImplTest {
    @Mock
    private IEmployeeService iEmployeeService;

    @InjectMocks
    private IEmployeeControllerImpl iEmployeeControllerImpl;

    @Test
    void testGetAllEmployees() {
        when(iEmployeeService.getAllEmployees()).thenReturn(getEmployees());
        ResponseEntity<List<Employee>> allEmployees = iEmployeeControllerImpl.getAllEmployees();
        assertEquals(HttpStatus.OK, allEmployees.getStatusCode());
        assertEquals(2, allEmployees.getBody().size());
    }

    @Test
    void testGetEmployeesByNameSearch() {
        when(iEmployeeService.getEmployeesByNameSearch("Sanjivani")).thenReturn(getEmployees());
        ResponseEntity<List<Employee>> employeesByNameSearch = iEmployeeControllerImpl.getEmployeesByNameSearch("Sanjivani");
        assertEquals(HttpStatus.OK, employeesByNameSearch.getStatusCode());
        assertEquals(2, employeesByNameSearch.getBody().size());
    }

    @Test
    void testGetEmployeeById() {
        when(iEmployeeService.getEmployeeById("1")).thenReturn(getEmployees().get(0));
        ResponseEntity<Employee> employeeById = iEmployeeControllerImpl.getEmployeeById("1");
        assertEquals(HttpStatus.OK, employeeById.getStatusCode());
        assertEquals("Sanjivani", employeeById.getBody().getName());
    }

    @Test
    void testGetHighestSalaryOfEmployees() {
        when(iEmployeeService.getHighestSalaryOfEmployees()).thenReturn(10000);
        ResponseEntity<Integer> highestSalaryOfEmployees = iEmployeeControllerImpl.getHighestSalaryOfEmployees();
        assertEquals(HttpStatus.OK, highestSalaryOfEmployees.getStatusCode());
        assertEquals(10000, highestSalaryOfEmployees.getBody());
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        when(iEmployeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(List.of("Sanjivani", "Shriraj"));
        ResponseEntity<List<String>> topTenHighestEarningEmployeeNames = iEmployeeControllerImpl.getTopTenHighestEarningEmployeeNames();
        assertEquals(HttpStatus.OK, topTenHighestEarningEmployeeNames.getStatusCode());
        assertEquals(2, topTenHighestEarningEmployeeNames.getBody().size());
    }

    @Test
    void testCreateEmployee() {
        Employee employee = Employee.builder().id("3").name("John").title("QA").age(25).salary(15000).build();
        when(iEmployeeService.createEmployee(getEmployeeInput())).thenReturn(employee);
        ResponseEntity<Employee> createEmployee = iEmployeeControllerImpl.createEmployee(getEmployeeInput());
        assertEquals(HttpStatus.OK, createEmployee.getStatusCode());
        assertEquals(employee, createEmployee.getBody());
    }

    @Test
    void testDeleteEmployeeById() {
        when(iEmployeeService.deleteEmployeeById("1")).thenReturn("Sanjivani");
        ResponseEntity<String> deleteEmployeeById = iEmployeeControllerImpl.deleteEmployeeById("1");
        assertEquals(HttpStatus.OK, deleteEmployeeById.getStatusCode());
        assertEquals("Sanjivani", deleteEmployeeById.getBody());
    }
}