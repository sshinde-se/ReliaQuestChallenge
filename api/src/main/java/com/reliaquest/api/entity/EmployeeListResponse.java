package com.reliaquest.api.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeListResponse {
    private List<Employee> data;
}
