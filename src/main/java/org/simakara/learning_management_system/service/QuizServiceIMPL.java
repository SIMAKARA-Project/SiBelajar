package org.simakara.learning_management_system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simakara.learning_management_system.dto.request.CreateQuizRequest;
import org.simakara.learning_management_system.dto.request.UpdateQuizCourseRequest;
import org.simakara.learning_management_system.dto.request.UpdateQuizRequest;
import org.simakara.learning_management_system.dto.response.QuizResponse;
import org.simakara.learning_management_system.handler.ValidatorHandler;
import org.simakara.learning_management_system.mapper.QuizResponseMapper;
import org.simakara.learning_management_system.model.Course;
import org.simakara.learning_management_system.model.Quiz;
import org.simakara.learning_management_system.model.enums.QuizType;
import org.simakara.learning_management_system.repository.CourseRepository;
import org.simakara.learning_management_system.repository.QuizRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.simakara.learning_management_system.mapper.QuizResponseMapper.toQuizResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuizServiceIMPL implements QuizService {

    private final QuizRepository quizRepo;

    private final CourseRepository courseRepo;

    private final ValidatorHandler validatorHandler;

    @Override
    public QuizResponse createQuiz(CreateQuizRequest request) {

        log.info("Create quiz request received, validating...");

        validatorHandler.validate(request);

        if (quizRepo.existsByName(request.name())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Quiz: " + request.name() + " already exist."
            );
        }

        if (
                QuizType.TRYOUT.equals(request.type())
                && Objects.isNull(request.expiration())
        ) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Expiration date is required for tryouts"
            );
        }

        if (request.accessible().isAfter(request.expiration())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Expiration cannot be before accessible date"
            );
        }

        log.info("Validated. Creating quiz...");

        List<Course> courses = request
                .courseCodes()
                .stream()
                .distinct()
                .map(
                        code -> courseRepo
                                .findByCode(code)
                                .orElseThrow(
                                        () -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "Course with code " + code + " not found."
                                        )
                                )
                )
                .toList();

        Quiz quiz = Quiz.builder()
                .name(request.name())
                .description(request.description())
                .type(request.type())
                .accessibleAt(request.accessible())
                .expiredAt(request.expiration())
                .courses(courses)
                .build();

        log.info("Quiz Created");

        quizRepo.save(quiz);

        return toQuizResponse(quiz);
    }

    @Override
    public QuizResponse getQuizInfo(String code) {

        Quiz quiz = quizRepo
                .findByCode(code)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Quiz doesn't exist."
                        )
                );

        return toQuizResponse(quiz);
    }

    @Override
    public List<QuizResponse> getQuizzes(String name, int page, int content) {

        if (name.isBlank()) {

            log.info("Finding all quizzes");

            return quizRepo
                    .findAll(PageRequest.of(page, content))
                    .stream()
                    .map(QuizResponseMapper::toQuizResponse)
                    .toList();
        }

        log.info("Finding quizzes with name containing {}", name);

        return quizRepo
                .findByNameContainingIgnoreCase(name, PageRequest.of(page, content))
                .stream()
                .map(QuizResponseMapper::toQuizResponse)
                .toList();
    }

    @Override
    public List<QuizResponse> getQuizzesFromCourse(String code, int page, int content) {

        Course course = courseRepo
                .findByCode(code)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Course doesn't exist.")
                );

        return quizRepo
                .findByCourses(
                        course,
                        PageRequest.of(page, content)
                )
                .stream()
                .map(QuizResponseMapper::toQuizResponse)
                .toList();
    }

    @Override
    @Transactional
    public QuizResponse updateQuiz(UpdateQuizRequest request, String code) {

        validatorHandler.validate(request);

        Quiz quiz = quizRepo.findByCode(code)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Quiz doesn't exist."
                        )
                );

        String name = request.name();
        String description = request.description();
        QuizType type = request.type();
        LocalDateTime expiration = request.expiration();

        if (!name.isBlank()) {
            quiz.setName(name);
        }

        if (!description.isBlank()) {
            quiz.setDescription(description);
        }

        if (Objects.nonNull(type) && !type.toString().isBlank()) {
            quiz.setType(type);
        }

        if (Objects.nonNull(expiration) && !expiration.toString().isBlank()) {
            if (quiz.getAccessibleAt().isAfter(expiration)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Expiration cannot be before accessible date"
                );
            }

            quiz.setExpiredAt(expiration);
        }

        quizRepo.save(quiz);

        return toQuizResponse(quiz);
    }

    @Override
    @Transactional
    public QuizResponse updateQuizCourse(UpdateQuizCourseRequest request, String code) {

        validatorHandler.validate(request);

        Quiz quiz = quizRepo.findByCode(code)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz doesn't exist")
                );

        if (request.courseCode().isEmpty()) {
            return toQuizResponse(quiz);
        }

        List<Course> courses = request.courseCode()
                .stream()
                .distinct()
                .map(
                        courseCode -> courseRepo
                                .findByCode(courseCode)
                                .orElseThrow(
                                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course doesn't exist")
                                )
                )
                .filter(
                        course -> !quiz.getCourses().contains(course)
                )
                .toList();

        quiz.getCourses().addAll(courses);

        quizRepo.save(quiz);

        return toQuizResponse(quiz);
    }

    @Override
    @Transactional
    public QuizResponse deleteQuizCourse(String courseCode, String quizCode) {

        Quiz quiz = quizRepo.findByCode(quizCode)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Quiz doesn't exist"
                        )
                );

        Course course = courseRepo.findByCode(courseCode)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Course doesn't exist"
                        )
                );

        if (!quiz.getCourses().contains(course)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Course " + courseCode + " is not included in " + quizCode
            );
        }

        quiz.getCourses().remove(course);

        quizRepo.save(quiz);

        return toQuizResponse(quiz);
    }

    @Override
    @Transactional
    public void deleteQuiz(String code) {

        Quiz quiz = quizRepo
                .findByCode(code)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Quiz doesn't exist"
                        )
                );

        quizRepo.delete(quiz);
    }
}
