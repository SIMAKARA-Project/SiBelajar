package org.simakara.learning_management_system.mapper;

import org.simakara.learning_management_system.dto.response.EnrollResponse;
import org.simakara.learning_management_system.model.Course;
import org.simakara.learning_management_system.model.User;

import java.util.List;

public class EnrollResponseMapper {

    public static EnrollResponse toEnrollResponse(Course course, List<User> users) {
        return new EnrollResponse(
                course.getCode(),
                users.stream()
                        .map(User::fullName)
                        .toList()
        );
    }
}
