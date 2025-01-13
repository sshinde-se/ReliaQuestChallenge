package com.reliaquest.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class EmployeeResponse {
    private String status;
    private Employee data;
    private String error;
}
