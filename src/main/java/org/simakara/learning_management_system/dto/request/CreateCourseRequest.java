package org.simakara.learning_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCourseRequest(
        @NotBlank(message = "Course name is required!")
        String name,

        String description
) {
}
