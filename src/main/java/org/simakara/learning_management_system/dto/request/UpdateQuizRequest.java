package org.simakara.learning_management_system.dto.request;

import org.simakara.learning_management_system.model.enums.QuizType;

import java.time.LocalDateTime;

public record UpdateQuizRequest(
        String name,
        String description,
        QuizType type,
        LocalDateTime expiration
) {
}
