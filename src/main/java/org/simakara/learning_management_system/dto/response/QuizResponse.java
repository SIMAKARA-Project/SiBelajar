package org.simakara.learning_management_system.dto.response;

import java.time.LocalDateTime;

public record QuizResponse(
        String name,
        String description,
        String code,
        String timeLeft
) {
}
