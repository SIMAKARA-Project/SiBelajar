package org.simakara.learning_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UpdateUserRequest(
        String firstname,
        String lastname,
        String email,
        LocalDate dateOfBirth,
        @NotBlank(message = "Password needed to change your attributes")
        String currentPassword
) {
}
