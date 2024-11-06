package com.ahicode.dtos;

import lombok.*;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorDto {
    private String message;
    private Map<String, String> errors;
}
