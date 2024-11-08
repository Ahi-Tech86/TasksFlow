package com.ahicode.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreationRequest {
    @NotNull
    @NotBlank(message = "Project name is mandatory")
    @Size(min = 3, max = 75, message = "Project name must be between 3 and 75 characters")
    private String name;
    @NotNull
    @NotBlank(message = "Project description is mandatory")
    private String description;
}
