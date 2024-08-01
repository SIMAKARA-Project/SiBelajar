package org.simakara.learning_management_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank
        String firstname,

        String lastname,

        @NotBlank
        String username,

        @NotBlank @Size(min = 8)
        String password,

        @Email
        String email
) {
}
