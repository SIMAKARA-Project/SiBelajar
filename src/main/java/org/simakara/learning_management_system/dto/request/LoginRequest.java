package org.simakara.learning_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "username needed to login")
        String username,
        @NotBlank(message = "password needed to login")
        String password
) {
}
