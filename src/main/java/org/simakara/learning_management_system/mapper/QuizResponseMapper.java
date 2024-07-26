package org.simakara.learning_management_system.mapper;

import org.simakara.learning_management_system.dto.response.QuizResponse;
import org.simakara.learning_management_system.model.Quiz;

import static org.simakara.learning_management_system.tools.TimeLeftCalculator.timeLeft;

public class QuizResponseMapper {

    public static QuizResponse toQuizResponse(Quiz quiz) {
        return new QuizResponse(
                quiz.getName(),
                quiz.getDescription(),
                quiz.getCode(),
                timeLeft(quiz.getExpiredAt())
        );
    }
}
