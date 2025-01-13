package com.reliaquest.api.service;

import com.reliaquest.api.entity.*;
import com.reliaquest.api.enums.ErrorCode;
import com.reliaquest.api.feign.EmployeeFeignClient;
import com.reliaquest.api.util.CustomLogger;
import com.reliaquest.api.util.ExceptionUtil;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class IEmployeeServiceImpl implements IEmployeeService{

    private CustomLogger customLogger = new CustomLogger(IEmployeeServiceImpl.class);
    @Autowired
    private EmployeeFeignClient employeeFeignClient;


    @Override
    public List<Employee> getAllEmployees() {
        customLogger.info("Getting All Employees");
        try{
            EmployeeListResponse employeeListResponse = employeeFeignClient.getAllEmployees();
            List<Employee> employees = employeeListResponse.getData();

            if(CollectionUtils.isEmpty(employees)){
                customLogger.error("getAllEmployees: No employees found");
                throw ExceptionUtil.getAPIException(ErrorCode.NO_RECORDS_FOUND);
            }
            return employeeListResponse.getData();
        }
        catch (FeignException feignException){
            customLogger.error("getAllEmployees: Exception occurred while getting all employees: "+feignException.getMessage());
            throw ExceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API);
        }
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String searchString) {
        customLogger.info("Getting Employees by name search");
        try{
            EmployeeListResponse employeeListResponse = employeeFeignClient.getAllEmployees();
            List<Employee> employees = employeeListResponse.getData().stream()
                    .filter(employee -> employee.getName().toLowerCase().contains(searchString.toLowerCase()))
                    .toList();
            if(CollectionUtils.isEmpty(employees)){
                customLogger.error("getEmployeesByNameSearch: No employees found for search string: "+ searchString);
                throw ExceptionUtil.getAPIException(ErrorCode.NO_RECORDS_FOUND);
            }
            return employees;
        }
        catch (FeignException feignException){
            customLogger.error("getEmployeesByNameSearch: Exception while getting employees by search: "+feignException.getMessage());
            throw ExceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API);
        }
    }

    @Override
    public Employee getEmployeeById(String id) {
        customLogger.info("Getting employee by ID");
        try{
            EmployeeResponse employeeResponse = employeeFeignClient.getEmployeeById(UUID.fromString(id));

            if(Objects.isNull(employeeResponse.getData())){
                customLogger.error("getEmployeeById: No employee record found for id: "+ id);
                throw ExceptionUtil.getAPIException(ErrorCode.NO_RECORDS_FOUND);
            }
            return employeeResponse.getData();
        }
        catch (FeignException.NotFound feignException) {
            customLogger.error("getEmployeeById: Employee with id " + id + " not found. FeignException: " + feignException.getMessage());
            throw ExceptionUtil.getAPIException(ErrorCode.NO_RECORDS_FOUND);
        }
        catch (FeignException feignException){
            customLogger.error("getEmployeeById: Exception while getting employees by id: "+feignException.getMessage());
            throw ExceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API);
        }
    }

    @Override
    public Integer getHighestSalaryOfEmployees() {
        customLogger.info("Getting highest salary of employees");
        try{
            EmployeeListResponse employeeListResponse = employeeFeignClient.getAllEmployees();
            return employeeListResponse.getData().stream()
                    .mapToInt(Employee::getSalary)
                    .max()
                    .orElse(0);
        }
        catch (FeignException feignException){
            customLogger.error("getHighestSalaryOfEmployees: Exception while getting highest salary of employees: "+feignException.getMessage());
            throw ExceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API);
        }
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() {
        customLogger.info("Getting top 10 highest earning employee names");
        try{
            EmployeeListResponse employeeListResponse = employeeFeignClient.getAllEmployees();
            return employeeListResponse.getData().stream()
                    .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
                    .limit(10)
                    .map(Employee::getName)
                    .toList();
        }catch (FeignException feignException){
            customLogger.error("getTopTenHighestEarningEmployeeNames: Exception while getting top 10 highest earning employee names: "+feignException.getMessage());
            throw ExceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API);
        }
    }

    @Override
    public Employee createEmployee(EmployeeRequest request) {
        customLogger.info("Creating employee");
        try{
            EmployeeResponse employeeResponse = employeeFeignClient.createEmployee(request);
            Employee employee = employeeResponse.getData();
            customLogger.info("Employee created successfully: "+ employeeResponse);
            return employee;
        }catch (FeignException feignException){
            customLogger.error("createEmployee: Exception while creating employee: "+feignException.getMessage());
            throw ExceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API);
        }
    }

    @Override
    public String deleteEmployeeById(String id) {
        customLogger.info("Deleting employee by id");
        try{
            Employee employee = getEmployeeById(id);
            DeleteRequest deleteRequest = DeleteRequest.builder().name(employee.getName()).build();
            employeeFeignClient.deleteEmployeeById(deleteRequest);
            customLogger.info("deleteEmployeeById: Employee deleted successfully: "+ id);
            return employee.getName();
        }catch (FeignException feignException){
            customLogger.error("deleteEmployeeById: Exception while deleting employee by id: "+ id + "."+ feignException.getMessage());
            throw ExceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API);
        }
    }
}
