package com.reliaquest.api.dataprovider;

import com.reliaquest.api.entity.Employee;
import com.reliaquest.api.entity.EmployeeListResponse;
import com.reliaquest.api.entity.EmployeeRequest;
import com.reliaquest.api.entity.EmployeeResponse;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;

import java.util.*;

public class EmployeeDataProvider {
    public static Employee getEmployee() {
        return Employee.builder()
                .id("a4d7ea02-e9fd-42b8-88bb-33ba9eca76ca")
                .name("Sanjivani")
                .salary(10000)
                .age(26)
                .title("Software Developer").build();
    }
    public static EmployeeResponse getEmployeeResponse(){
        return EmployeeResponse.builder()
                .data(getEmployee()).status("Successfully processed request.").build();
    }
    public static EmployeeResponse getInvalidEmployeeResponse(){
        return EmployeeResponse.builder()
                .data(Employee.builder().id("a4d7ea02-e9fd-42b8-88bb-33ba9eca73ca").name("ABC")
                        .title("Dev").age(3).build()).status("Successfully processed request.").build();
    }
    public static Employee getEmployee2() {
        return Employee.builder()
                .id("a4d7ea02-e9fd-42b8-88bb-33ba9eca71ca")
                .name("Shriraj")
                .salary(5000)
                .age(18)
                .title("QA").build();
    }

    public static List<Employee> getEmployees() {
        return Arrays.asList(getEmployee(), getEmployee2());
    }
    public static EmployeeListResponse getEmployeesResponse() {
        EmployeeListResponse employeeListResponse=new EmployeeListResponse();
        employeeListResponse.setData(getEmployees());
        return employeeListResponse;
    }

    public static EmployeeRequest getEmployeeInput() {
        EmployeeRequest employeeInput = new EmployeeRequest();
        employeeInput.setName("John");
        employeeInput.setSalary(15000);
        employeeInput.setAge(25);
        employeeInput.setTitle("QA");
        return employeeInput;
    }

    public static EmployeeRequest getInvalidEmployeeInput() {
        EmployeeRequest employeeInput = new EmployeeRequest();
        employeeInput.setName("John");
        employeeInput.setSalary(15000);
        employeeInput.setAge(25);
        employeeInput.setTitle("7!");
        return employeeInput;
    }

    public static EmployeeListResponse getEmptyEmployeeResponse() {
        EmployeeListResponse employeeListResponse=new EmployeeListResponse();
        employeeListResponse.setData(Collections.emptyList());
        return employeeListResponse;
    }
}
