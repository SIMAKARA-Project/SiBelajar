package org.simakara.learning_management_system.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateQuizCourseRequest(
        @NotNull
        List<String> courseCode
) {
}
