package org.simakara.learning_management_system.mapper;

import org.simakara.learning_management_system.dto.response.UserResponse;
import org.simakara.learning_management_system.model.User;

import java.util.Objects;

import static org.simakara.learning_management_system.tools.DateFormater.format;

public class UserResponseMapper {

    public static UserResponse toUserResponse(User user) {

        if (Objects.nonNull(user.getDateOfBirth())) {
            return new UserResponse(
                    user.fullName(),
                    user.getUsername(),
                    user.getEmail(),
                    format(user.getDateOfBirth())
            );
        }
        return new UserResponse(
                user.fullName(),
                user.getUsername(),
                user.getEmail(),
                null
        );
    }
}
