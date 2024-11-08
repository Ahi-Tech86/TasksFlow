package com.ahicode.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    private String message;

    @JsonCreator
    public ErrorDto(@JsonProperty("message") String message) {
        this.message = message;
    }
}