package org.simakara.learning_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.simakara.learning_management_system.dto.request.CreateQuizRequest;
import org.simakara.learning_management_system.dto.request.UpdateQuizCourseRequest;
import org.simakara.learning_management_system.dto.request.UpdateQuizRequest;
import org.simakara.learning_management_system.dto.response.QuizResponse;
import org.simakara.learning_management_system.dto.response.WebResponse;
import org.simakara.learning_management_system.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        path = "/api/v2/quiz"
)
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping(
            path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<QuizResponse>> createQuiz(
            @RequestBody CreateQuizRequest request
    ) {
        QuizResponse response = quizService.createQuiz(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }

    @GetMapping(
            path = "/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<QuizResponse>> getQuizInfo(
            @PathVariable(value = "code") String code
    ) {
        QuizResponse response = quizService.getQuizInfo(code);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }

    @GetMapping(
            path = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<List<QuizResponse>>> getQuizzes(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "pg") int page,
            @RequestParam(value = "ctn") int content
    ) {
        List<QuizResponse> responses = quizService.getQuizzes(
                name, page, content
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                responses,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }

    @GetMapping(
            path = "/course/{courseCode}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<List<QuizResponse>>> getQuizzesFromCourse(
            @PathVariable(value = "courseCode") String code,
            @RequestParam(value = "pg") int page,
            @RequestParam(value = "ctn") int content
    ) {
        List<QuizResponse> responses = quizService.getQuizzesFromCourse(code, page, content);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                responses,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }

    @PatchMapping(
            path = "/update/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<QuizResponse>> updateQuiz(
            @RequestBody UpdateQuizRequest request,
            @PathVariable(value = "code") String code
    ) {
        QuizResponse response = quizService.updateQuiz(request, code);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }

    @PostMapping(
            path = "/update/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<QuizResponse>> updateQuizCourse(
            @RequestBody UpdateQuizCourseRequest request,
            @PathVariable(value = "code") String courseCode
    ) {
        QuizResponse response = quizService.updateQuizCourse(request, courseCode);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }

    @PostMapping(
            path = "/remove/{courseCode}/{quizCode}"
    )
    public ResponseEntity<WebResponse<QuizResponse>> deleteQuizCourse(
            @PathVariable(value = "courseCode") String courseCode,
            @PathVariable(value = "quizCode") String quizCode
    ) {
        QuizResponse response = quizService.deleteQuizCourse(courseCode, quizCode);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }

    @DeleteMapping(
            path = "/delete/{code}"
    )
    public ResponseEntity<WebResponse<String>> deleteQuiz(
            @PathVariable(value = "code") String code
    ) {
        quizService.deleteQuiz(code);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                "Quiz deleted",
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }
}
