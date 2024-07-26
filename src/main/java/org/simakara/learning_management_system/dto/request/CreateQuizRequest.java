package org.simakara.learning_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.simakara.learning_management_system.model.enums.QuizType;

import java.time.LocalDateTime;
import java.util.List;

public record CreateQuizRequest(
        @NotBlank(message = "Quiz name is required.")
        String name,

        String description,

        @NotNull
        QuizType type,

        @NotEmpty
        List<String> courseCodes,

        @NotNull
        LocalDateTime accessible,

        LocalDateTime expiration
) {
}
