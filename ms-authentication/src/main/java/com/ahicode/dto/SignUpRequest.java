package com.ahicode.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @NotNull @Email @Max(50)
    @Schema(description = "User's email", example = "example@mail.com")
    private String email;
    @NotNull @Max(50)
    @Schema(description = "User nickname in the application", example = "UserNickname007")
    private String nickname;
    @NotNull @Max(35)
    @Schema(description = "User's firstname", example = "Firstname")
    private String firstname;
    @NotNull @Max(35)
    @Schema(description = "User's firstname", example = "Lastname")
    private String lastname;
    @NotNull @Min(1)
    @Schema(description = "User's password", example = "password")
    private String password;
}
