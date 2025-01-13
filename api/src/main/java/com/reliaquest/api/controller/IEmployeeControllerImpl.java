package com.reliaquest.api.controller;

import com.reliaquest.api.entity.Employee;
import com.reliaquest.api.entity.EmployeeRequest;
import com.reliaquest.api.service.IEmployeeService;
import com.reliaquest.api.util.CustomLogger;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IEmployeeControllerImpl implements IEmployeeController<Employee, EmployeeRequest> {

    private CustomLogger customLogger = new CustomLogger(IEmployeeControllerImpl.class);
    private IEmployeeService iEmployeeService;
    public IEmployeeControllerImpl(IEmployeeService iEmployeeService){
        this.iEmployeeService = iEmployeeService;
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        StopWatch stopWatch = customLogger.startStopWatch();
        List<Employee> employees = iEmployeeService.getAllEmployees();
        customLogger.logEndTime(stopWatch, "getAllEmployees");
        return ResponseEntity.ok(employees);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        StopWatch stopWatch = customLogger.startStopWatch();
        List<Employee> employees = iEmployeeService.getEmployeesByNameSearch(searchString);
        customLogger.logEndTime(stopWatch, "getEmployeesByNameSearch");
        return ResponseEntity.ok(employees);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        StopWatch stopWatch = customLogger.startStopWatch();
        Employee employee = iEmployeeService.getEmployeeById(id);
        customLogger.logEndTime(stopWatch, "getEmployeeById");
        return ResponseEntity.ok(employee);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        StopWatch stopWatch = customLogger.startStopWatch();
        Integer highestSalary = iEmployeeService.getHighestSalaryOfEmployees();
        customLogger.logEndTime(stopWatch, "getHighestSalaryOfEmployees");
        return ResponseEntity.ok(highestSalary);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        StopWatch stopWatch = customLogger.startStopWatch();
        List<String> topTenHighestEarningEmployeeNames = iEmployeeService.getTopTenHighestEarningEmployeeNames();
        customLogger.logEndTime(stopWatch, "getTopTenHighestEarningEmployeeNames");
        return ResponseEntity.ok(topTenHighestEarningEmployeeNames);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(EmployeeRequest employeeInput) {
        StopWatch stopWatch = customLogger.startStopWatch();
        Employee employee = iEmployeeService.createEmployee(employeeInput);
        customLogger.logEndTime(stopWatch, "createEmployee");
        return ResponseEntity.ok(employee);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        StopWatch stopWatch = customLogger.startStopWatch();
        String name = iEmployeeService.deleteEmployeeById(id);
        customLogger.logEndTime(stopWatch, "deleteEmployeeById");
        return ResponseEntity.ok(name);
    }
}
