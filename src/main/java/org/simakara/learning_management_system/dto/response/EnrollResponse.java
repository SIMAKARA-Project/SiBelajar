package org.simakara.learning_management_system.dto.response;

import java.util.List;

public record EnrollResponse(
        String courseCode,
        List<String> studentNames
) {
}
