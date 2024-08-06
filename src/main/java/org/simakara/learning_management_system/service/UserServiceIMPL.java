package org.simakara.learning_management_system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simakara.learning_management_system.dto.request.UpdateUserRequest;
import org.simakara.learning_management_system.dto.response.UserResponse;
import org.simakara.learning_management_system.handler.ValidatorHandler;
import org.simakara.learning_management_system.model.User;
import org.simakara.learning_management_system.repository.CourseRepository;
import org.simakara.learning_management_system.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Objects;

import static org.simakara.learning_management_system.mapper.UserResponseMapper.toUserResponse;
import static org.simakara.learning_management_system.tools.NameFormater.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceIMPL implements UserService{

    private final UserRepository userRepo;

    private final CourseRepository courseRepo;

    private final ValidatorHandler validatorHandler;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getUser(String username) {
        log.info("Getting user info from {}", username);

        User user = userRepo.findByUsername(username)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        )
                );

        log.info("Responding...");

        return toUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(UpdateUserRequest request, String currentUsername) {
        log.info("Validating request...");

        validatorHandler.validate(request);

        log.info("Request validated. Processing request...");

        User user = userRepo.findByUsername(currentUsername)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        )
                );

        log.info("validating authentication...");

        validatorHandler.validateAuth(user);

        log.info("Validated. Processing extra password matching layer...");

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password");
        }

        log.info("Password matched. Editing {} fields", currentUsername);

        String firstname = request.firstname();
        String lastname = request.lastname();
        String email = request.email();
        LocalDate dateOfBirth = request.dateOfBirth();

        if (Objects.nonNull(firstname) && !firstname.isBlank()) {
            log.info("Setting new first name...");

            user.setFirstname(format(firstname));
        }

        if (Objects.nonNull(lastname) && !lastname.isBlank()) {
            log.info("Setting new last name...");

            user.setLastname(format(lastname));
        }

        if (Objects.nonNull(email) && !email.isBlank()) {
            log.info("Setting new email...");

            user.setEmail(email);
        }

        if (Objects.nonNull(dateOfBirth)) {
            log.info("Setting new date of birth...");

            user.setDateOfBirth(dateOfBirth);
        }

        log.info("Saving changes...");

        userRepo.save(user);

        log.info("Saved successfully");

        return toUserResponse(user);
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        log.info("Finding user with username: {}...", username);

        User user = userRepo.findByUsername(username)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        )
                );

        log.info("User found. Deleting user...");

        user.getCourses().forEach(
                course -> {
                    course.getStudents().remove(user);
                    courseRepo.save(course);
                }
        );

        userRepo.delete(user);

        log.info("User deleted successfully");
    }
}
