package com.reliaquest.api.service;

import com.reliaquest.api.entity.Employee;
import com.reliaquest.api.entity.EmployeeRequest;

import java.util.List;

public interface IEmployeeService {
    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByNameSearch(String searchName);
    Employee getEmployeeById(String id);
    Integer getHighestSalaryOfEmployees();
    List<String> getTopTenHighestEarningEmployeeNames();
    Employee createEmployee(EmployeeRequest employee);
    String deleteEmployeeById(String id);
}
