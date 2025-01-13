package com.reliaquest.api.feign;

import com.reliaquest.api.entity.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.UUID;

@FeignClient(name = "employeeClient", url = "http://localhost:8112/api/v1/employee")
public interface EmployeeFeignClient {

    @GetMapping
    EmployeeListResponse getAllEmployees();

    @GetMapping("/{id}")
    EmployeeResponse getEmployeeById(@PathVariable("id") UUID id);

    @PostMapping
    EmployeeResponse createEmployee(@RequestBody EmployeeRequest request);

    @DeleteMapping
    void deleteEmployeeById(@RequestBody DeleteRequest deleteRequest);
}
