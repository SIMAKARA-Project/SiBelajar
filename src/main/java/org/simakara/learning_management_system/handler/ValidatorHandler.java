package org.simakara.learning_management_system.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ValidatorHandler {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private final Validator validator = factory.getValidator();

    public void validate (Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }
}
