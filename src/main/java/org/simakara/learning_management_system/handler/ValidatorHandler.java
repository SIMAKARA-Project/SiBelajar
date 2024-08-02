package org.simakara.learning_management_system.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.simakara.learning_management_system.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Component
public class ValidatorHandler {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private final Validator validator = factory.getValidator();

    public void validate (Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

    public void validateAuth(User user) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        var principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            if (!user.getUsername().equals(userDetails.getUsername())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid authentication");
        }
    }
}
