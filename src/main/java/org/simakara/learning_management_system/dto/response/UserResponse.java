package org.simakara.learning_management_system.dto.response;

public record UserResponse(
        String fullName,
        String username,
        String email,
        String dateOfBirth
) {
}
