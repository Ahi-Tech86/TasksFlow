package com.ahicode.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    @NotNull @Email
    @Schema(description = "User's email", example = "example@mail.com")
    private String email;
    @NotNull
    @Schema(description = "User's password", example = "password")
    private String password;
}
