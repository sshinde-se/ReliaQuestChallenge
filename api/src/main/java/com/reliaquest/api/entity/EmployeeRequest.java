package com.reliaquest.api.entity;

import lombok.Data;

@Data
public class EmployeeRequest {
    private String name;
    private Integer salary;
    private Integer age;
    private String title;
}
