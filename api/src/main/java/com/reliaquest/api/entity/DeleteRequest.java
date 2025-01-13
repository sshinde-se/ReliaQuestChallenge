package com.reliaquest.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class DeleteRequest {
    private String name;
}
