package com.ahicode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationRegisterRequest {
    @NotNull @Email
    @Schema(description = "User's email", example = "example@mail.com")
    private String email;
    @NotNull @Max(6)
    @JsonProperty("confirmation_code")
    @Schema(description = "The confirmation code that was sent to the user's email", example = "123456")
    private String confirmationCode;
}
